package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.mapper.ProjectDetailMapper;
import com.example.autoimpactanalysis.service.IProjectDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: ProjectDetailServiceImpl
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ProjectDetail 服务实现类
 */
@Service
@DS("mysql")
public class ProjectDetailServiceImpl extends ServiceImpl<ProjectDetailMapper, ProjectDetail> implements IProjectDetailService {
    @Autowired
    private ProjectDetailMapper projectDetailMapper;
    @Override
    public List<ProjectDetail> findList() {
        QueryWrapper<ProjectDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("is_delete", "1");
        queryWrapper.orderByAsc("project_code");
        List<ProjectDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ProjectDetail> getByProjectId(String projectId) {
        QueryWrapper<ProjectDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(projectId), "project_id", projectId);
        queryWrapper.ne("is_delete", "1");
        List<ProjectDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ProjectDetail> getByProjectCode(String projectCode) {
        QueryWrapper<ProjectDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(projectCode), "project_code", projectCode);
        queryWrapper.ne("is_delete", "1");
        List<ProjectDetail> list = list(queryWrapper);
        return list;
    }
    @Override
    public int removeByProjectId(String projectId) {
        ProjectDetail projectDetail = new ProjectDetail();
        UpdateWrapper<ProjectDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(StringUtils.hasText(projectId), "project_id", projectId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("is_delete", "1");
        return projectDetailMapper.update(projectDetail,updateWrapper);
    }

    @Override
    public int removeBatchByProjectIds(List<String> projectIds) {
        ProjectDetail projectDetail = new ProjectDetail();
        try {
            UpdateWrapper<ProjectDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ne("is_delete", "1");
            updateWrapper.set("is_delete", "1").in("project_id",projectIds);
            return projectDetailMapper.update(projectDetail,updateWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
