package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import com.example.autoimpactanalysis.entity.ReportStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName: IReportStatisticsService
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatistics 服务接口
 */
public interface IReportStatisticsService extends IService<ReportStatistics> {

    IPage<ReportStatisticsVO> findReports(Integer pageNum, Integer pageSize, QueryWrapper<ReportStatisticsVO> queryWrapper);

    List<ReportStatistics> getByReportCode(String reportId);

    List<ReportStatistics> getByProjectId(String projectId);
}
