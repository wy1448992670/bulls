package com.goochou.p2b.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.model.Product;
import com.goochou.p2b.service.ProductService;
import com.goochou.p2b.service.ProjectPackageService;
import com.goochou.p2b.service.ProjectService;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {//产品

    @Resource
    private ProductService productService;
    @Resource
    private ProjectPackageService projectPackageService;
    @Resource
    private ProjectService projectService;

    /**
     * @Description: 产品列表
     * @date  2017/2/15
     * @author 王信
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String productList(Model model, Integer status, Integer page) {
        if (page == null) {
            page = 1;
        }
        List<Product> list = productService.selectProductList(status,(page-1)*20,20);
        Integer count = productService.selectProductCount(status);
        model.addAttribute("list", list);
        model.addAttribute("pages", calcPage(count, 20));
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "product/list";
    }

    @RequestMapping(value = "/ajaxProductList", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> ajaxProductList(Integer status) {
        List<Product> list = productService.selectProductList(null,null,null);
        return list;
    }

    /**
     * @Description: 新增产品
     * @date  2017/2/15
     * @author 王信
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "product/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model,Product product) {
//        if(product.getAnnualizedMin()!=null){
//            Float min=product.getAnnualizedMin()/100;
//            product.setAnnualizedMin(min);
//        }
//        if(product.getAnnualizedMax()!=null){
//            Float max=product.getAnnualizedMax()/100;
//            product.setAnnualizedMax(max);
//        }
        product.setCreateDate(new Date());
        productService.saveProduct(product);
        return "redirect:/product/list";
    }

    /**
     * @Description: 更新产品
     * @date  2017/2/15
     * @author 王信
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model,Integer id) {
        model.addAttribute("product",productService.selectById(id));
        model.addAttribute("projectCount",projectService.selectByStatusAndProductIdCount(5,1,id));
        return "product/update";
    }

}