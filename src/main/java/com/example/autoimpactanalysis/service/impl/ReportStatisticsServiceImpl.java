package com.example.autoimpactanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import com.example.autoimpactanalysis.entity.*;
import com.example.autoimpactanalysis.mapper.ProjectDetailMapper;
import com.example.autoimpactanalysis.mapper.ReportStatisticsMapper;
import com.example.autoimpactanalysis.service.IReportStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: ReportStatisticsServiceImpl
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatistics 服务实现类
 */
@Slf4j
@Service
public class ReportStatisticsServiceImpl extends ServiceImpl<ReportStatisticsMapper, ReportStatistics> implements IReportStatisticsService {

    @Autowired
    private ReportStatisticsMapper reportStatisticsMapper;

    @Override
    public IPage<ReportStatisticsVO> findReports(Integer pageNum, Integer pageSize, QueryWrapper<ReportStatisticsVO> queryWrapper) {
        IPage<ReportStatisticsVO> reportStatisticsIPage = reportStatisticsMapper.findReports(new Page<>(pageNum, pageSize), queryWrapper);
        return reportStatisticsIPage;
    }

    @Override
    public List<ReportStatistics> getByReportCode(String reportId) {
        QueryWrapper<ReportStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportId), "report_id", reportId);
        List<ReportStatistics> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportStatistics> getByProjectId(String projectId) {
        QueryWrapper<ReportStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(projectId), "project_id", projectId);
        List<ReportStatistics> list = list(queryWrapper);
        return list;
    }
}
