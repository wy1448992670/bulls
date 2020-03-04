//package com.test;
//
//import com.goochou.p2b.admin.util.ExcelUtil;
//import com.goochou.p2b.model.CustomerList;
//import com.goochou.p2b.model.User;
//import com.goochou.p2b.service.CustomerListService;
//import com.goochou.p2b.service.IconService;
//import com.goochou.p2b.service.UserService;
//import org.junit.Test;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
//@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
//public class ServiceTest extends AbstractJUnit4SpringContextTests {
//
//    @Resource
//    private UserService userService;
//    @Resource
//    private IconService iconService;
//    @Resource
//    private CustomerListService customerListService;
//
//    @Test
//    public void test() throws Exception {
//        List<String> phones = ExcelUtil.getExcelAsFile("d:\\c.xlsx");
//        for (String p : phones) {
//            System.out.println("--------------" + p);
//            User u = userService.getByPhone(p);
//            System.out.println("---------------------" + u);
//
//            CustomerList l = new CustomerList();
//            l.setAdminId(26);
//            l.setUserId(u.getId());
//            l.setCreateTime(new Date());
//            customerListService.save(l);
//        }
//    }
//
//}
