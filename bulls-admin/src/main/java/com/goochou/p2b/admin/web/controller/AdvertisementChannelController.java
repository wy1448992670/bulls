package com.goochou.p2b.admin.web.controller;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.AdvertisementChannel;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.AdvertisementChannelSumVo;
import com.goochou.p2b.service.AdvertisementChannelService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 渠道管理 controller
 * </p>
 *
 * @author shuys
 * @since 2019年10月12日 09:57:00
 */
@Controller
@RequestMapping(value = "/advertisementChannel")
public class AdvertisementChannelController extends BaseController {

    private static final Logger logger = Logger.getLogger(AdvertisementChannelController.class);

    @Resource
    private AdvertisementChannelService advertisementChannelService;

    @Resource
    private UploadService uploadService;

    /**
     * 跳转到添加banner页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAddAdvertisementChannel(Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.checkPermission("advertisement:add");
        }catch (Exception e) {
            model.addAttribute("error", "您没有权限操作");
            return "error";
        }
        return "advertisementChannel/add";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, HttpSession session,
                            AdvertisementChannel advertisementChannel,
                            @RequestParam("file") MultipartFile file
                        ) {
        try {
            if (advertisementChannel.getId() == null) {
                boolean exsitChannelNo = advertisementChannelService.validChannelNo(advertisementChannel.getChannelNo());
                if (exsitChannelNo) {
                    model.addAttribute("error", "渠道号已存在");
                    return "error";
                }
            }
            Map<String, Object> map = null;
            UserAdmin u = (UserAdmin) session.getAttribute("user");
            if (file != null && file.getSize() != 0){
                map = uploadService.save(file, 19, u.getId());
                advertisementChannel.setTopImageId(Integer.parseInt(map.get(ConstantsAdmin.ID).toString()));
            }
            advertisementChannelService.save(advertisementChannel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "redirect:/advertisementChannel/list";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(Model model, @RequestParam Integer id,Integer type) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("advertisement:detail");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            AdvertisementChannel advertisementChannel = advertisementChannelService.getMapper().selectByPrimaryKey(id);
            Upload upload = uploadService.get(advertisementChannel.getTopImageId());
            model.addAttribute("advertisementChannel", advertisementChannel);
            model.addAttribute("upload", upload);
            model.addAttribute("type", type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "advertisementChannel/detail";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,  Integer page,
    		String channelNo,String channelName, Integer channelType, Integer status, Date startDate, Date endDate) {
    	try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("advertisement:list");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            int limit = 20;
    		if (page == null || page == 0) {
    			page = 1;
    		}
    		
    		Date endDateNew = DateUtil.getAbsoluteDate(endDate, Calendar.DATE, 1);

            List<AdvertisementChannel> list = advertisementChannelService.list(channelNo, channelName, channelType, status, startDate, endDateNew, (page - 1) * limit, limit);
            int count = advertisementChannelService.countList(channelNo, channelName, channelType, status, startDate, endDateNew);
            int pages = calcPage(count, limit);

            for (AdvertisementChannel advertisementChannel : list) {
            	if(advertisementChannel.getTopImageId()!=null) {
            		Upload upload = uploadService.get(advertisementChannel.getTopImageId());
            		if(upload!=null) {
            			advertisementChannel.setTopImagePath(ClientConstants.ALIBABA_PATH + "upload/" + upload.getPath());
            		}
            	}
			}
            
			model.addAttribute("page", page);
			model.addAttribute("list", list);
			model.addAttribute("pages", pages);
			model.addAttribute("startDate", startDate != null ? DateFormatTools.dateToStr2(startDate) : null);
	        model.addAttribute("endDate", endDate != null ? DateFormatTools.dateToStr2(endDate) : null);
			model.addAttribute("channelType", channelType);
			model.addAttribute("channelName", channelName);
			model.addAttribute("channelNo", channelNo);
			model.addAttribute("status", status);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "advertisementChannel/list";
    }
    
    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    public String sum(Model model,  Integer page,
    		String channelNo,String channelName, Integer channelType,Integer status, Date userCreateDateStart, Date userCreateDateEnd) {
    	try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("advertisement:sum");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
            int limit = 20;
    		if (page == null || page == 0) {
    			page = 1;
    		}
    		Date endDateNew = DateUtil.getAbsoluteDate(userCreateDateEnd, Calendar.DATE, 1);
    		
            List<AdvertisementChannelSumVo> list = advertisementChannelService.selectSum(channelNo, channelName, channelType,status, userCreateDateStart, endDateNew, (page - 1) * limit, limit);
            int count = advertisementChannelService.countList(channelNo, channelName, channelType, status, null, null);
            int pages = calcPage(count, limit);


			model.addAttribute("page", page);
			model.addAttribute("list", list);
			model.addAttribute("pages", pages);
			model.addAttribute("userCreateDateStart", userCreateDateStart!= null ? DateFormatTools.dateToStr2(userCreateDateStart) : null);
			model.addAttribute("userCreateDateEnd", userCreateDateEnd!= null ? DateFormatTools.dateToStr2(userCreateDateEnd) : null);
			model.addAttribute("channelType", channelType);
			model.addAttribute("channelName", channelName);
			model.addAttribute("channelNo", channelNo);
			model.addAttribute("status", status);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "advertisementChannel/sum";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateStatus(Model model, Integer id, Integer status) {
        try {
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("advertisement:update");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
            
            AdvertisementChannel channel = advertisementChannelService.getMapper().selectByPrimaryKey(id);
            channel.setStatus(status);
            advertisementChannelService.updateForVersion(channel);
        
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        
        return "redirect:/advertisementChannel/list";
    }

}
