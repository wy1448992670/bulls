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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.CommonUtil;
import com.goochou.p2b.utils.StringEncrypt;

@Controller(value = "/")
public class CommonController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class);
	
    @Resource
    private UserAdminService userAdminService;
    @Resource
    private UserService userService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
	private DepartmentService departmentService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
    	
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String user = request.getParameter("user");
            String vcode = request.getParameter("verify");
            UserAdmin adminTemp = JSON.parseObject(user, UserAdmin.class);
//            UserAdmin admin = userAdminService.getByUsername(adminTemp.getUsername());
            
            /*if(admin == null){
                map.put("status", false);
                map.put("msg", "未知账户");
                AjaxUtil.str2front(response, JSON.toJSONString(map));
                return;
            }*/
            /*Object obj = session.getAttribute("count");
            Integer errorCount = obj == null ? 0 : (Integer)obj;
            if (errorCount != null && errorCount >= 3) {
                if (StringUtils.isBlank(vcode)) {
                    map.put("status", false);
                    map.put("msg", "请输入验证码");
                    map.put("code", "vcodeError");
                    AjaxUtil.str2front(response, JSON.toJSONString(map));
                    return;
                }
                String verifyCode = (String) session.getAttribute(Constants.JCAP_SESSION_KEY);
                if (!vcode.equalsIgnoreCase(verifyCode)) {
                	map.put("status", false);
                    map.put("msg", "验证码错误");
                    AjaxUtil.str2front(response, JSON.toJSONString(map));
                    return;
                }
            }
            
            long now = new Date().getTime();
            if (errorCount != null && errorCount >= 5 && (now - admin.getLastLoginTime().getTime() < 1000 * 60 * 60)) {
                map.put("status", false);
                map.put("msg", "您已输错5次, 账号已被锁定");
                AjaxUtil.str2front(response, JSON.toJSONString(map));
                return;
            }*/
            
        	UsernamePasswordToken token = new UsernamePasswordToken(adminTemp.getUsername(), StringEncrypt.Encrypt(adminTemp.getPassword()));
            token.setHost(CommonUtil.getIpAddr(request));
            String remember = request.getParameter("remember");
            if ("true".equals(remember)) {
                token.setRememberMe(true);
            }
            // 获取当前登录的subject
            Subject currentUser = SecurityUtils.getSubject();
            try {
                // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
                // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
                // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            	session.setAttribute("count", 0);
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证开始");
                currentUser.login(token);
                logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证通过");
            } catch (UnknownAccountException uae) {
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证未通过,未知账户");
                map.put("status", false);
                map.put("msg", "未知账户");
                /*errorCount ++;
            	session.setAttribute("count", errorCount);
            	map.put("leftCount", 5 - errorCount);*/
            } catch (IncorrectCredentialsException ice) {
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
                map.put("status", false);
                map.put("msg", "密码不正确");
            } catch (LockedAccountException lae) {
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证未通过,账户已锁定");
                map.put("status", false);
                map.put("msg", "账户已锁定");
            } catch (ExcessiveAttemptsException eae) {
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证未通过,错误次数过多");
                map.put("status", false);
                map.put("msg", "用户名或密码错误次数过多");
            } catch (AuthenticationException ae) {
                // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            	logger.info("对用户[" + adminTemp.getUsername() + "]进行登录验证..验证未通过");
                map.put("status", false);
                map.put("msg", "用户名或密码不正确");
                logger.error(ae);
            }
            
            if (currentUser.isAuthenticated()) {
                map.put("status", true);
                session.setAttribute("user", currentUser.getSession().getAttribute("user"));
            }
            
            AjaxUtil.str2front(response, JSON.toJSONString(map));
        } catch (Exception e) {
        	logger.error(e);
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/noperm", method = RequestMethod.GET)
    public String noperm() {
        return "no-perm";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpSession session) {
        if (session.getAttribute("listResource") == null) {
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
        }
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, HttpSession session,Integer departmentId) {
        try {
        	Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("home:all");
            }catch (Exception e) {
            	model.addAttribute("error", "您没有权限查看");
            	return "error";
            }
            UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=admin.getId();
            Integer adminDepartmentId=admin.getDepartmentId();
            		
			try {
				subject.checkPermission("user:seealluser");
				adminId=null;
				adminDepartmentId=0;
			} catch (Exception e) {
			}
            
            // 查询实际用户总数
            Integer realUserCount = userService.count(0,adminDepartmentId,departmentId);
            // 查询物权交易额
            Double investmentAmount = investmentService.getAmountAll(adminId,departmentId);
            // 查询商城交易额
            Double goodsAmount = goodsOrderService.getAmountCount(adminId,departmentId);
            // 资产排行榜
            List<Map<String, Object>> list1 = assetsService.getRankList(adminId,departmentId,0);
            // 投资排行榜
            List<Map<String, Object>> list2 = assetsService.getRankList(adminId,departmentId,1);
            // 收益排行榜
            List<Map<String, Object>> list3 = assetsService.getRankList(adminId,departmentId,2);

            model.addAttribute("realUserCount", realUserCount);
            model.addAttribute("investmentAmount", investmentAmount);
            model.addAttribute("goodsAmount", goodsAmount);
            model.addAttribute("list1", list1);
            model.addAttribute("list2", list2);
            model.addAttribute("list3", list3);
            model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getShowDepartments(adminDepartmentId));
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error(e);
        }
        return "home";
    }
}
