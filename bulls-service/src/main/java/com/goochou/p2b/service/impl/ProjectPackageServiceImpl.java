package com.goochou.p2b.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProductMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.ProjectPackageMapper;
import com.goochou.p2b.model.Product;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.ProjectPackage;
import com.goochou.p2b.model.ProjectPackageExample;
import com.goochou.p2b.service.ProjectPackageService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class ProjectPackageServiceImpl implements ProjectPackageService {
	private final static Logger logger = Logger.getLogger(ProjectPackageServiceImpl.class);
    @Resource
    private ProjectPackageMapper projectPackageMapper;
    @Resource
    private ProjectService projectService;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProjectMapper projectMapper;

    @Override
    public List<ProjectPackage> selectProjectPackageList(Integer status, Integer product, Integer start, Integer limit) {
        ProjectPackageExample example = new ProjectPackageExample();
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        ProjectPackageExample.Criteria criteria = example.createCriteria();
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (product != null) {
            criteria.andProductIdEqualTo(product);
        }
        example.setOrderByClause("id desc");
        return projectPackageMapper.selectByExample(example);
    }

    @Override
    public Integer selectProjectPackageCount(Integer status, Integer product) {
        ProjectPackageExample example = new ProjectPackageExample();
        ProjectPackageExample.Criteria criteria = example.createCriteria();
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (product != null) {
            criteria.andProductIdEqualTo(product);
        }
        return projectPackageMapper.countByExample(example);
    }

    @Override
    public void savePackage(Integer product, Integer[] projectId) throws Exception {
        List<Project> list = projectService.selectPackageList(product, null, null);
        if (list.size() == 0) {
            return;
        }
        Product pd = productMapper.selectByPrimaryKey(product);
        ProjectPackage g = new ProjectPackage();
        g.setCreateTime(new Date());
        g.setInvestedAmount(0.0);
        g.setProductId(product);
        g.setStatus(0);
        ProjectPackageExample example = new ProjectPackageExample();
        example.createCriteria().andProductIdEqualTo(product);
        g.setTitle(pd.getName() + "第" + (projectPackageMapper.countByExample(example) + 1) + "期");
        Double totalAmount = 0.0;
        for (Integer integer : projectId) {
            for (Project project : list) {
                if (integer.equals(project.getId())) {
                    totalAmount += project.getTotalAmount();
                }
            }
        }
        g.setTotalAmount(totalAmount);
        projectPackageMapper.insertSelective(g);
        for (Integer integer : projectId) {
            for (Project project : list) {
                if (integer.equals(project.getId())) {
                    if (project.getProjectType().equals(5)) {
                        project.setStatus(2);
                    }
                    if (project.getProjectType().equals(6)) {
                        project.setStatus(2);
                    }
//                    project.setPackageId(g.getId());
                    projectService.updateAndLock(project);
                }
            }
        }
    }

    @Override
    public ProjectPackage selectByIdProjectPackage(Integer packageId) {
        return projectPackageMapper.selectByPrimaryKey(packageId);
    }

    @Override
    public void updatePackage(Integer packageId, Integer status, Integer[] projectId) throws Exception {
        List<Integer> projectList = Arrays.asList(projectId);
        Date date = new Date();
        ProjectPackage pack = projectPackageMapper.selectByPrimaryKey(packageId);
        if (pack.getStatus().equals(0) && status == 1) {
            pack.setStatus(1);
            pack.setStartTime(date);
        }
        ProjectExample projectExample = new ProjectExample();
//        projectExample.createCriteria().andPackageIdEqualTo(packageId);
        List<Project> list = projectMapper.selectByExample(projectExample);
        Double amount = 0.0;
        for (Project project : list) {
            if (!projectList.contains(project.getId())) {
//                project.setPackageId(0);
                if (project.getProjectType().equals(5)) {
                    project.setStatus(1);
                }
                if (project.getProjectType().equals(6)) {
                    project.setStatus(0);
                }
                projectService.updateAndLock(project);
                amount += project.getTotalAmount();
            } else {
                if (status == 1) {
                    project.setStartTime(date);
                    projectService.updateAndLock(project);
                }

            }
        }
        amount = BigDecimalUtil.sub(pack.getTotalAmount(), amount);
        pack.setTotalAmount(amount);
        projectPackageMapper.updateByPrimaryKeySelective(pack);//更新资产包总金额.
    }

    @Override
    public Integer saveNewPackage(Integer productId) {//资产包自动打包方法
        Integer ret = 0;
        try {
            ProjectPackageExample example = new ProjectPackageExample();
            example.createCriteria().andStatusEqualTo(0);
            List<ProjectPackage> projectPackageList = projectPackageMapper.selectByExample(example);
            if (projectPackageList.size() > 0) {
                ProjectPackage projectPackage = projectPackageList.get(0);
                projectPackage.setStatus(1);
                projectPackageMapper.updateByPrimaryKeySelective(projectPackage);

                //修改改包下面现有项目状态到2
                ProjectExample projectExample = new ProjectExample();
//                projectExample.createCriteria().andPackageIdEqualTo(projectPackage.getId());

                List<Project> projects = projectMapper.selectByExample(projectExample);
                if (null != projects && !projects.isEmpty()) {
                    for (Project p : projects) {
                        p.setStatus(2); //投资中
                        projectService.updateByPrimaryKeySelectiveForVersion(p);

                    }
                }
                return 0;
            }
            List<Project> list = projectService.selectPackageList(productId, null, null);
            if (list.size() == 0) {
                return 1;//当前资产库没有可以打包的项目
            }
            ProjectPackage g = new ProjectPackage();
            g.setCreateTime(new Date());
            g.setInvestedAmount(0.0);
            g.setProductId(productId);
            g.setStatus(1);
            ProjectPackageExample ex = new ProjectPackageExample();
            ex.createCriteria().andProductIdEqualTo(productId);
            g.setTitle(productMapper.selectByPrimaryKey(productId).getName() + "第" + (projectPackageMapper.countByExample(ex) + 1) + "期");
            Double totalAmount = 0.0;
            for (Project project : list) {
                totalAmount += project.getTotalAmount();
            }
            g.setTotalAmount(totalAmount);
            g.setStartTime(new Date());
            projectPackageMapper.insertSelective(g);
            for (Project project : list) {
                if (project.getProjectType().equals(5)) {
                    project.setStatus(2);
                }
                if (project.getProjectType().equals(6)) {
                    project.setStatus(2);
                }
//                project.setPackageId(g.getId());
                projectService.updateByPrimaryKeySelectiveForVersion(project);
            }
        } catch (Exception e) {
        	logger.error(e);
            return 2; //异常
        }
        return ret;
    }

    @Override
    public Integer selectByProductIdCount(Integer productId) {
        ProjectPackageExample example = new ProjectPackageExample();
        example.createCriteria().andProductIdEqualTo(productId);
        return projectPackageMapper.countByExample(example);
    }

    @Override
    public Integer selectByProductIdandStatusCount(Integer productId) {
        ProjectPackageExample example = new ProjectPackageExample();
        example.createCriteria().andProductIdEqualTo(productId).andStatusEqualTo(1);
        return projectPackageMapper.countByExample(example);
    }
}
