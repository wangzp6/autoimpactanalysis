package com.example.autoimpactanalysis.service;

import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName: IProjectDetailService
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ProjectDetail 服务接口
 */
public interface IProjectDetailService extends IService<ProjectDetail> {
    List<ProjectDetail> findList();

    List<ProjectDetail> getByProjectId(String projectId);

    List<ProjectDetail> getByProjectCode(String projectCode);

    int removeByProjectId(String projectId);

    int removeBatchByProjectIds(List<String> projectIds);

}
