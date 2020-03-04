package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.dao.RoleMapper;
import com.goochou.p2b.model.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.service.AdminRoleService;
import com.goochou.p2b.service.RoleServices;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.CommonUtil;
import com.goochou.p2b.utils.StringEncrypt;

@Controller
@RequestMapping(value = "/user")
public class UserAdminController extends BaseController {

	private static final Logger logger = Logger.getLogger(UserAdminController.class);

    @Resource
    private UserAdminService userAdminService;
    @Resource
    private UploadService uploadService;
    @Resource
    private RoleServices roleServices;
    @Resource
    private AdminRoleService adminRoleService;
    @Resource
    private RoleMapper roleMapper;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute User user, Model model) {
        List<Department> departments = userAdminService.getAllDepartment();
        model.addAttribute("departments", departments);
        
        return "/user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute UserAdmin user, Model model, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:add");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            user.setCreateTime(new Date());
            user.setCreateIp(CommonUtil.getIpAddr(request));
            user.setStatus(1);
            user.setPassword(StringEncrypt.Encrypt(user.getPassword()));
            userAdminService.save(user);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.GET)
    public void checkName(@RequestParam String username, HttpServletResponse response) {
        Boolean flag = true;
        try {
            flag = userAdminService.checkNameExists(username);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(flag));
    }

    @RequestMapping(value = "/delete")
    public String delete(int id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:edit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = userAdminService.get(id);
            admin.setStatus(2);
            userAdminService.update(admin);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(int id, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:edit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            String roleName = userAdminService.getRoleNameByUserId(admin.getId());
            model.addAttribute("roleName", roleName);
            UserAdmin user = userAdminService.get(id);
            List<AdminRole> adminRoleList = adminRoleService.getByUserId(user.getId());
            user.setRoleList(adminRoleList);
            if (adminRoleList != null && !adminRoleList.isEmpty()) {
                AdminRole ar = adminRoleList.get(0);
                model.addAttribute("roleId", ar.getRoleId());
            }
            List<Role> list = roleServices.findAllRole();
            model.addAttribute("list", list);
            model.addAttribute("user", user);
            List<Department> departments = userAdminService.getAllDepartment();
            model.addAttribute("departments", departments);
        } catch (Exception e) {
        	logger.error(e);
        	e.printStackTrace();

        }
        return "/user/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute UserAdmin user, int roleId, Model model, HttpSession session) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:edit");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            UserAdmin u = userAdminService.get(user.getId());
            u.setUpdateTime(new Date());
            u.setUpdateUser(((UserAdmin) session.getAttribute("user")).getId());
            u.setEmail(user.getEmail());
            u.setSex(user.getSex());
            u.setStatus(user.getStatus());
            u.setMonthPlan(user.getMonthPlan());
            u.setDayPlan(user.getDayPlan());
            u.setDepartmentId(user.getDepartmentId());
            userAdminService.update(u);
            List<AdminRole> adminRoleList = adminRoleService.getByUserId(u.getId());
            if (adminRoleList != null && adminRoleList.size() > 0) {
                AdminRole adminRole = adminRoleList.get(0);
                if (roleId > 0) {
                    adminRole.setAdminId(u.getId());
                    adminRole.setRoleId(roleId);
                    adminRoleService.update(adminRole);
                }
            } else {
                AdminRole adminRole = new AdminRole();
                if (roleId > 0) {
                    adminRole.setAdminId(u.getId());
                    adminRole.setRoleId(roleId);
                    adminRoleService.save(adminRole);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/checkOldPassword", method = RequestMethod.GET)
    public void checkOldPassword(String oldPassword, HttpServletResponse response, HttpSession session) {
        Boolean flag = true;
        try {
            flag = userAdminService.checkOldPassword(((UserAdmin) session.getAttribute("user")).getId(), oldPassword);
        } catch (Exception e) {
            logger.error(e);
        }
        AjaxUtil.str2front(response, JSON.toJSONString(flag));
    }

    @RequestMapping(value = "/edit/password", method = RequestMethod.GET)
    public String editPassword(Model model, HttpSession session) {
        try {
            model.addAttribute("user", session.getAttribute("user"));
        } catch (Exception e) {
            logger.error(e);
        }
        return "/user/edit-password";
    }

    @RequestMapping(value = "/edit/avatar", method = RequestMethod.GET)
    public String editAvatar(Model model, HttpSession session) {
        return "/user/upload";
    }

    /**
     * 上传头像
     *
     * @param file
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/edit/avatar", method = RequestMethod.POST)
    public String editAvatar(@RequestParam MultipartFile file, HttpServletRequest request, HttpSession session) {
        try {
            String newAvatar = userAdminService.updateAvatar(((UserAdmin) session.getAttribute("user")).getId(), file);
            if (newAvatar != null) {
                // 更新session中的头像
                UserAdmin u = (UserAdmin) session.getAttribute("user");
                u.setAvatar(newAvatar);
                session.setAttribute("user", u);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/user/edit/avatar";
    }

    @RequestMapping(value = "/edit/password", method = RequestMethod.POST)
    public String editPassword(@ModelAttribute UserAdmin user, HttpSession session) {
        try {
            UserAdmin u = userAdminService.get(user.getId());
            u.setUpdateTime(new Date());
            u.setUpdateUser(((UserAdmin) session.getAttribute("user")).getId());
            u.setPassword(StringEncrypt.Encrypt(user.getPassword()));
            userAdminService.update(u);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/home";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list")
    public String query(Model model, @RequestParam(required = false) String
            keyword, 
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Integer page) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:view");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            int limit = 20;
            if (page == null) {
                page = 1;
            }
            Map<String, Object> map = userAdminService.query(keyword, status, roleId,(page - 1) * limit, limit);
            int count = (Integer) map.get("count");
            List<UserAdmin> users = (List<UserAdmin>) map.get("list");
            
            List<Role> roles = roleMapper.selectByExample(new RoleExample());

            int pages = calcPage(count, limit);
            model.addAttribute("pages", pages);
            model.addAttribute("users", users);
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);
            model.addAttribute("roleId", roleId);
            model.addAttribute("roles", roles);
            return "/user/list";
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(int id, Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:detail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            UserAdmin admin = userAdminService.detail(id);
            model.addAttribute("user", admin);
        } catch (Exception e) {
            logger.error(e);
        }
        return "/user/detail";
    }
}
