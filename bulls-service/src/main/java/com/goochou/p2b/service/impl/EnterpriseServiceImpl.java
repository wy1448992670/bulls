package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.EnterpriseMapper;
import com.goochou.p2b.dao.EnterprisePictureMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.model.Enterprise;
import com.goochou.p2b.model.EnterpriseExample;
import com.goochou.p2b.model.EnterpriseExample.Criteria;
import com.goochou.p2b.model.EnterprisePicture;
import com.goochou.p2b.model.EnterprisePictureExample;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
	private static final Logger logger = Logger.getLogger(EnterpriseServiceImpl.class);
    @Resource
    private EnterpriseMapper enterpriseMapper;
    @Resource
    private EnterprisePictureMapper enterprisePictureMapper;
    @Resource
    private UploadMapper uploadMapper;

    public List<Enterprise> query(String keyword, int start, int limit,Integer type) throws Exception {
        try {
            EnterpriseExample example = new EnterpriseExample();
            Criteria c = example.createCriteria();
            if (!StringUtils.isEmpty(keyword)) {
                c.andNameLike("%" + keyword + "%");
            }
            if(type!=null){
                c.andTypeEqualTo(type);
            }
            example.setLimitStart(start);
            example.setLimitEnd(limit);
            example.setOrderByClause("id desc");
            return enterpriseMapper.selectByExample(example);
        } catch (Exception e) {
        	logger.error(e);
            throw new Exception(e);
        }
    }

    public Integer queryCount(String keyword,Integer type) {
        EnterpriseExample example = new EnterpriseExample();
        Criteria c = example.createCriteria();
        if(type!=null){
            c.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(keyword)) {
            c.andNameLike("%" + keyword + "%");
        }
        return enterpriseMapper.countByExample(example);
    }

    public Enterprise detail(Integer id) {
        Enterprise e = enterpriseMapper.selectByPrimaryKey(id);
        if (e != null) {
            EnterprisePictureExample example = new EnterprisePictureExample();
            com.goochou.p2b.model.EnterprisePictureExample.Criteria c = example.createCriteria();
            c.andEnterpriseIdEqualTo(e.getId());
            List<EnterprisePicture> list = enterprisePictureMapper.selectByExample(example);
            if (null != list && !list.isEmpty()) {
                for (EnterprisePicture p : list) {
                    if (p != null) {
                        Upload upload = uploadMapper.selectByPrimaryKey(p.getUploadId());
                        if (upload != null) {
                            p.setPicturePath(upload.getPath());
                        }
                    }
                }
            }
            e.setEnterprisePicture(list);
        }
        return e;
    }

    public boolean checkNameExists(String name, Integer id) throws Exception {
        if (id != null) {
            Enterprise e = enterpriseMapper.selectByPrimaryKey(id);
            if (e.getName().equals(name)) { // 无更改则不需要验证
                return true;
            }
        }
        EnterpriseExample example = new EnterpriseExample();
        Criteria c = example.createCriteria();
        c.andNameEqualTo(StringUtils.trim(name));
        List<Enterprise> list = enterpriseMapper.selectByExample(example);
        return list.size() > 0 ? false : true;
    }

    public void saveWithPicture(Enterprise enterprise, String picture) throws Exception {
        enterpriseMapper.insert(enterprise);
        if (StringUtils.isNotBlank(picture)) {
            String[] ps = picture.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
    }

    @Override
    public void saveWithPicture(Enterprise enterprise, String picture, String picture2) throws Exception {
        enterpriseMapper.insert(enterprise);
        if (StringUtils.isNotBlank(picture)) {
            String[] ps = picture.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    p.setType(0);//执照类型
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
        if (StringUtils.isNotBlank(picture2)) {
            String[] ps = picture2.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    p.setType(1);//公章类型
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
    }

    public void update(Enterprise enterprise, String picture) throws Exception {
        enterpriseMapper.updateByPrimaryKey(enterprise);
        if (StringUtils.isNotBlank(picture)) {
            String[] ps = picture.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
    }

    @Override
    public void update(Enterprise enterprise, String picture, String picture2) throws Exception {
        enterpriseMapper.updateByPrimaryKeySelective(enterprise);
        if (StringUtils.isNotBlank(picture)) {
            String[] ps = picture.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    p.setType(0);
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
        if (StringUtils.isNotBlank(picture2)) {
            String[] ps = picture2.split(",");
            if (ps != null && ps.length > 0) {
                for (int i = 0; i < ps.length; i++) {
                    EnterprisePicture p = enterprisePictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
                    p.setEnterpriseId(enterprise.getId());
                    p.setStatus(0);
                    p.setType(1);
                    enterprisePictureMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
    }

    @Override
    public String selectEnterpriseSealPath(Integer investmentId) {
        return enterprisePictureMapper.selectEnterpriseSealPath(investmentId);
    }

    @Override
    public Enterprise selectByPrimaryKey(Integer id){
        return enterpriseMapper.selectByPrimaryKey(id);
    }

	@Override
	public Enterprise selectByUserId(Integer userId) {

		EnterpriseExample example = new EnterpriseExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<Enterprise> list = enterpriseMapper.selectByExample(example);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

    @Override
    public Enterprise selectFirstRecord() {
        EnterpriseExample example = new EnterpriseExample();
        example.setLimitStart(0);
        example.setLimitEnd(1);
        example.setOrderByClause(" id asc ");
        example.createCriteria();
        List<Enterprise> list = enterpriseMapper.selectByExample(example);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
