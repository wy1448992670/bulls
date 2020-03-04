package com.goochou.p2b.app.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.model.User;
import com.twovv.captcha4j.ProducerI;
import com.twovv.captcha4j.utils.Config;


@Controller
@RequestMapping("/captcha4j")
@Api(value = "captcha4j")
public class Captcha4jController extends BaseController {
	private static final Logger logger = Logger.getLogger(Captcha4jController.class);
    private ProducerI jcapProducer;
    private String sessionKeyValue = null;
    private Properties props = new Properties();

    @RequestMapping(value = "/getCaptImg", method = RequestMethod.GET)
    @ApiOperation(value = "获取图片验证码")
    public void getCaptImg(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
                           @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                           @ApiParam("app版本号") @RequestParam String appVersion,
                           @ApiParam("手机号") @RequestParam String phone) {

    	logger.info("注册手机==>phone=" + phone + "获取图像验证码");
        Object img = getCacheKeyValue((Constants.CAP_IMG_CODE + phone));
        if (img == null) {
            try {

                init(null);
            } catch (ServletException e) {
                e.printStackTrace();
            }
            resp.setDateHeader("Expires", 0L);
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
            resp.setHeader("Pragma", "no-cache");
            resp.setContentType("image/jpeg");
            String capText = this.jcapProducer.createText();
            req.getSession().setAttribute(this.sessionKeyValue, capText);
            memcachedManager.addOrReplace((Constants.CAP_IMG_CODE + phone).trim(), capText, 3600);
            logger.info("图形验证码：" + memcachedManager.get((Constants.CAP_IMG_CODE + phone).trim()));
            BufferedImage bi = this.jcapProducer.createImage(capText);

            ServletOutputStream out = null;
            try {
                out = resp.getOutputStream();
                ImageIO.write(bi, "jpg", out);

                try {
                    out.flush();
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init(ServletConfig conf) throws ServletException {
        ImageIO.setUseCache(false);
        if (conf != null) {
            Enumeration initParams = conf.getInitParameterNames();

            while (initParams.hasMoreElements()) {
                String key = (String) initParams.nextElement();
                String value = conf.getInitParameter(key);
                this.props.put(key, value);
            }
        }
        Config config = new Config(this.props);
        this.jcapProducer = config.getProducerImpl();
        this.sessionKeyValue = config.getSessionKey();
    }

    
    /**
     * web端获取随机码
    * @Title: getWebCaptImg 
    * @param req
    * @param resp
    * @param client
    * @param appVersion void
    * @author zj
    * @date 2019-08-02 17:58
     */
    @RequestMapping(value = "/getWebCaptImg", method = RequestMethod.GET)
    @ApiOperation(value = "获取图片验证码")
    public void getWebCaptImg(HttpServletRequest req, HttpServletResponse resp, 
                           @ApiParam("终端来源 IOS,Android,PC,WAP,WEB") @RequestParam String client,
                           @ApiParam("app版本号") @RequestParam String appVersion) {

    	 User user = (User)  	req.getSession().getAttribute("user");
   
        Object img = getCacheKeyValue((Constants.CAP_IMG_CODE + user.getUsername()));
        if (img == null) {
            try {

                init(null);
            } catch (ServletException e) {
                e.printStackTrace();
            }
            resp.setDateHeader("Expires", 0L);
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
            resp.setHeader("Pragma", "no-cache");
            resp.setContentType("image/jpeg");
            String capText = this.jcapProducer.createText();
            req.getSession().setAttribute(this.sessionKeyValue, capText);
            memcachedManager.addOrReplace((Constants.CAP_IMG_CODE + user.getUsername()).trim(), capText, 3600);
            logger.info("图形验证码：" + memcachedManager.get((Constants.CAP_IMG_CODE + user.getUsername()).trim()));
            BufferedImage bi = this.jcapProducer.createImage(capText);

            ServletOutputStream out = null;
            try {
                out = resp.getOutputStream();
                ImageIO.write(bi, "jpg", out);

                try {
                    out.flush();
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
