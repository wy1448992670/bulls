package com.goochou.p2b.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.goochou.p2b.admin.realm.MyRealm;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.Role;
import com.goochou.p2b.model.RoleResources;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.ResourcesService;
import com.goochou.p2b.service.RoleServices;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.utils.AjaxUtil;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);
	
    @Resource
    private UserAdminService userAdminService;
    @Resource
    private RoleServices roleServices;
    @Resource
    private ResourcesService resourcesService;

    @RequestMapping(value = "/list")
    public String findAllRole(Model model, @RequestParam(required = false) Integer page, String keyword, HttpSession session) {
        try {
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            List<Role> list = roleServices.findAllRole((page - 1) * limit, limit, keyword);
            int count = roleServices.getCount(keyword);
            int pages = calcPage(count, limit);
            model.addAttribute("pages", pages);
            model.addAttribute("list", list);
            model.addAttribute("page", page);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/resources/list-role";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Integer id, Model model) {
        Role role = roleServices.detail(id);
        model.addAttribute("role", role);
        return "/resources/detail-role";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model) {
        Role role = roleServices.detail(id);

        model.addAttribute("role", role);

        return "/resources/edit-role";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Role role) {
        try {
            roleServices.update(role);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/role/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Role role) {
        return "/resources/add-role";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Role role, HttpServletRequest request) {
        try {
            roleServices.save(role);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/role/list";
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    public void checkName(String role, HttpServletResponse response) {
        Boolean flag = true;
        try {
            flag = roleServices.checkNameExists(role);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(flag));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(required = true) int id) {
        try {
            roleServices.delete(id);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/role/list";
    }

    // 跳转配置权限
    @RequestMapping(value = "/toRoleResources", method = RequestMethod.GET)
    public String toRoleResources(@RequestParam(required = true) int roleId, Model model) {
        try {
            Role role = roleServices.detail(roleId);
            List<Resources> roleResource = roleServices.findById(roleId);// 获取该角色已有权限
            List<Resources> fatherList = resourcesService.findAllFather();// 所有父权限
            model.addAttribute("fatherList", fatherList);
            model.addAttribute("roleResource", roleResource);
            model.addAttribute("role", role);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/resources/detail-role";
    }

    @RequestMapping(value = "/showResources", method = RequestMethod.GET)
    public void showResources(@RequestParam(required = true) int roleId, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Resources> roleResource = roleServices.findById(roleId);
            map.put("roleResource", roleResource);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(map));
    }

    @Resource
    private MyRealm myRealm;

    @RequestMapping(value = "/editResources", method = RequestMethod.POST)
    public String batchUpdateRights(@RequestParam(required = true) int roleId, String resourcesId, Model model, HttpSession session) {
        try {
            int delete = resourcesService.deletRights(roleId);
            logger.info("[执行删除操作删除：" + delete + "条数据]");
            List<RoleResources> list = new ArrayList<RoleResources>();
            if (!StringUtils.isEmpty(resourcesId)) {
                String[] array = resourcesId.split(",");
                for (int i = 0; i < array.length; i++) {
                    RoleResources rr = new RoleResources();
                    rr.setRoleId(roleId);
                    rr.setResourceId(Integer.parseInt(array[i]));
                    list.add(rr);
                }
                int insert = resourcesService.batchInsertRights(list);
                logger.info("[执行插入操作，共：" + insert + "条数据]");
                logger.info("[需要插入：" + array.length + "条数据]");
                // 重新加载菜单栏权限
                // 通过session获取用户,然后获取用户id
                UserAdmin u = (UserAdmin) session.getAttribute("user");
                // 通过用户id查询,所有菜单权限
                List<Resources> resources = userAdminService.selectResourcesByAdminId(u.getId(), 1, 1, true);
                List<Resources> listResource = new ArrayList<Resources>();

                if (null != resources && resources.size() > 0) {
                    for (Resources resource : resources) {
                        if (null != resource.getParentId() && resource.getParentId() == 1) {
                            listResource.add(resource);
                        }
                    }
                    if (null != listResource && listResource.size() > 0) {
                        for (Resources parent : listResource) {
                            parent.setResources(userAdminService.selectResourcesByAdminId(u.getId(), 1, parent.getId(), false));
                        }
                    }
                }
                session.setAttribute("listResource", listResource);

                // 修改shiro中的权限
                myRealm.modifyCache();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/role/list";
    }


}
