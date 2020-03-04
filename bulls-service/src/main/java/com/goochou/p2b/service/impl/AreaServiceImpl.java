package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.AreaMapper;
import com.goochou.p2b.model.Area;
import com.goochou.p2b.model.AreaExample;
import com.goochou.p2b.model.vo.AreaIndexVO;
import com.goochou.p2b.service.AreaService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2020年01月07日 14:33:00
 */
@Service
public class AreaServiceImpl implements AreaService {
    private final static Logger logger = Logger.getLogger(AreaServiceImpl.class);
    
    @Resource
    private AreaMapper areaMapper;

    @Override
    public AreaMapper getMapper() {
        return areaMapper;
    }

    @Override
    public List<AreaIndexVO> getAreaByParentCode(String parentCode) {
        AreaExample example = new AreaExample();
        example.setOrderByClause(" source_code ");
        AreaExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(-1);
        if (StringUtils.isBlank(parentCode)) {
            criteria.andParentCodeIsNull();
        } else {
            criteria.andParentCodeEqualTo(parentCode);
        }
        return areaMapper.listAreaIndexByExample(example);
    }

    @Override
    public Area getAreaByCode(String code) throws Exception {
        AreaExample example = new AreaExample();
        AreaExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(-1);
        if (StringUtils.isBlank(code)) {
            throw new Exception("code不能为空");
        }
        criteria.andCodeEqualTo(code);
        List<Area> list = areaMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<AreaIndexVO> getAllArea() {
        AreaExample example = new AreaExample();
        example.setOrderByClause(" source_code ");
        AreaExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(-1);
        List<AreaIndexVO> areas = areaMapper.listAreaIndexByExample(example);
        return this.buidTree(areas);
    }

    private List<AreaIndexVO> buidTree(List<AreaIndexVO> list){
        List<AreaIndexVO> tree=new ArrayList<>();
        for(AreaIndexVO node : list){
            if (StringUtils.isBlank(node.getParentCode())) {
                tree.add(findChild(node, list));
            }
        }
        return tree;
    }
    
    private AreaIndexVO findChild(AreaIndexVO node, List<AreaIndexVO> list){
        for(AreaIndexVO areaIndexVO : list){
            if(areaIndexVO.getParentCode() != null && areaIndexVO.getParentCode().equals(node.getCode())){
                if(node.getCitys() == null){
                    node.setCitys(new ArrayList<>());
                }
                node.getCitys().add(findChild(areaIndexVO, list));
            }
        }
        return node;
    }

}
