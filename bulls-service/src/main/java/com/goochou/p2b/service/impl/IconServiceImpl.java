package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.IconGroupContactMapper;
import com.goochou.p2b.dao.IconGroupMapper;
import com.goochou.p2b.dao.IconMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.IconGroup;
import com.goochou.p2b.model.IconGroupContact;
import com.goochou.p2b.model.IconGroupExample;
import com.goochou.p2b.model.IconGroupExample.Criteria;
import com.goochou.p2b.service.IconService;

@Service
public class IconServiceImpl implements IconService {
    @Resource
    private IconMapper iconMapper;
    @Resource
    private IconGroupMapper iconGroupMapper;
    @Resource
    private IconGroupContactMapper iconGroupContactMapper;
    @Resource
    private UploadMapper uploadMapper;

    @Override
    public List<Icon> getUsingIcons(Integer type, String version) {
        return iconMapper.getUsingIcons(type, version);
    }

    @Override
    public List<IconGroup> query(Integer type, Integer status, String title, String version, Integer start, Integer limit) {
        IconGroupExample example = new IconGroupExample();
        Criteria c = example.createCriteria();
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }
        if (title != null) {
            c.andTitleLike("%"+title+"%");
        }
        if (version != null) {
            c.andVersionLike("%"+version+"%");
        }
        example.setLimitStart(start);
        example.setLimitEnd(limit);
		example.setOrderByClause("id desc");
        List<IconGroup> list = iconGroupMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            for (IconGroup iconGroup : list) {
                iconGroup.setPathList(getPicPathByGroupId(iconGroup.getId()));
            }
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getPicPathByGroupId(Integer groupId) {
        return iconMapper.getPicPathByGroupId(groupId);
    }

    @Override
    public Integer queryCount(Integer type, Integer status, String title, String version) {
        IconGroupExample example = new IconGroupExample();
        Criteria c = example.createCriteria();
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }
        if (title != null) {
            c.andTitleLike("%"+title+"%");
        }
        if (version != null) {
            c.andVersionLike("%"+version+"%");
        }
        return iconGroupMapper.countByExample(example);
    }

    @Override
    public void updateGroupStatus(Integer groupId, Integer status) {
        IconGroup icon = iconGroupMapper.selectByPrimaryKey(groupId);
        icon.setStatus(status);
        iconGroupMapper.updateByPrimaryKeySelective(icon);
    }

    @Override
    public List<Map<String, Object>> list(Integer start, Integer limit) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("start", start);
    	map.put("limit", limit);
        return iconMapper.list(map);
    }

    @Override
    public void saveWithPic(Integer linkId, Icon icon, Integer picture) {
    	IconGroupContact contact = null;
    	if(linkId == null || linkId == 0){
    		contact = new IconGroupContact();
    		icon.setCreateTime(new Date());
    		iconMapper.insert(icon);
    		
    		contact.setIconId(icon.getId());
            contact.setUploadId(picture);
            iconGroupContactMapper.insertSelective(contact);
    	}else{
    		contact = iconGroupContactMapper.selectByPrimaryKey(linkId);
    		icon.setCreateTime(new Date());
    		iconMapper.updateByPrimaryKeySelective(icon);
    		
    		contact.setIconId(icon.getId());
            contact.setUploadId(picture);
            iconGroupContactMapper.updateByPrimaryKey(contact);
    	}
    }

	@Override
	public void saveIconGroup(Integer id, String groupName, Integer type, Integer[] homeId, Integer[] home,Integer[] homeIcon, Integer[] meId, Integer[] me, Integer[] meIcon, String version, Integer status) {
		if(id == null){
			IconGroup group = new IconGroup();
			group.setTitle(groupName);
			group.setType(type);
			group.setVersion(version);
			group.setStatus(status);
			iconGroupMapper.insert(group);
			if(type == ConstantsAdmin.CONSTANT_ZERO.intValue()){
				for(int i=0; i<home.length; i++){
					IconGroupContact contact = new IconGroupContact();
					contact.setId(homeId[i]);
					contact.setIconId(homeIcon[i]);
					contact.setGroupId(group.getId());
					contact.setUploadId(home[i]);
					iconGroupContactMapper.updateByPrimaryKeySelective(contact);
				}
			}else{
				for(int i=0; i<me.length; i++){
					IconGroupContact contact = new IconGroupContact();
					contact.setId(meId[i]);
					contact.setIconId(meIcon[i]);
					contact.setGroupId(group.getId());
					contact.setUploadId(me[i]);
					iconGroupContactMapper.updateByPrimaryKeySelective(contact);
				}
			}
		}else{
			IconGroup group = iconGroupMapper.selectByPrimaryKey(id);
			iconGroupContactMapper.updateByGroupId(id);
			if(type == ConstantsAdmin.CONSTANT_ZERO.intValue()){
				for(int i=0; i<home.length; i++){
					IconGroupContact contact = new IconGroupContact();
					contact.setId(homeId[i]);
					contact.setIconId(homeIcon[i]);
					contact.setGroupId(group.getId());
					contact.setUploadId(home[i]);
					iconGroupContactMapper.updateByPrimaryKeySelective(contact);
				}
			}else{
				for(int i=0; i<me.length; i++){
					IconGroupContact contact = new IconGroupContact();
					contact.setId(meId[i]);
					contact.setIconId(meIcon[i]);
					contact.setGroupId(group.getId());
					contact.setUploadId(me[i]);
					iconGroupContactMapper.updateByPrimaryKeySelective(contact);
				}
			}
			group.setTitle(groupName);
			group.setType(type);
			group.setVersion(version);
			group.setStatus(status);
			iconGroupMapper.updateByPrimaryKeySelective(group);
		}
	}

	@Override
	public Map<String, Object> queryById(Integer id) {
		return iconMapper.queryById(id);
		
	}

	@Override
	public Integer queryListCount() {
		return iconMapper.queryListCount();
	}

}
