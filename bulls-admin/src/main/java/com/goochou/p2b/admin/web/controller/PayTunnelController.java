package com.goochou.p2b.admin.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goochou.p2b.model.PayTunnel;
import com.goochou.p2b.service.PayTunnelService;

@Controller
@RequestMapping(value = "/tunnel")
public class PayTunnelController extends BaseController {

    @Resource
    private PayTunnelService payTunnelService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String tunnelList(Model model, Integer page) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("tunnel:list");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限查看");
            return "error";
        }
        if (page == null) {
            page = 1;
        }
        int limit = 20;
        model.addAttribute("list", payTunnelService.list((page - 1) * limit, limit));
        Integer count = payTunnelService.listCount();
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        return "tunnel/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAddBanner() {
        return "tunnel/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBanner(PayTunnel tunnel, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("tunnel:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        payTunnelService.save(tunnel);
        return "redirect:/tunnel/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model) {
        PayTunnel t = payTunnelService.get(id);
        model.addAttribute("tunnel", t);
        return "tunnel/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(PayTunnel tunnel, Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("tunnel:edit");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        payTunnelService.update(tunnel);
        return "redirect:/tunnel/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id) {
        payTunnelService.delete(id);
        return "redirect:/tunnel/list";
    }

}
