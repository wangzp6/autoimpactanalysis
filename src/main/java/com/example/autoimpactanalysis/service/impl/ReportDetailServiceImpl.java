package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import com.example.autoimpactanalysis.mapper.ReportDetailMapper;
import com.example.autoimpactanalysis.service.IReportDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: ReportDetailServiceImpl
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: ReportDetail 服务实现类
 */
@Slf4j
@Service
@DS("mysql")
public class ReportDetailServiceImpl extends ServiceImpl<ReportDetailMapper, ReportDetail> implements IReportDetailService {

    @Autowired
    private ReportDetailMapper ReportDetailMapper;

    @Override
    public List<ReportDetail> findList() {
        log.info("进入ReportDetailServiceImpl/findList方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("report_type", "1");
        queryWrapper.ne("is_delete", "1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public IPage<ReportDetailVO> findReports(Integer pageNum, Integer pageSize, QueryWrapper<ReportDetailVO> queryWrapper) {
        log.info("进入ReportDetailServiceImpl/findReports方法");
        IPage<ReportDetailVO> ReportDetailIPage = ReportDetailMapper.findReports(new Page<>(pageNum, pageSize), queryWrapper);
        return ReportDetailIPage;
    }

    @Override
    public List<ReportDetail> getByReportId(String reportId) {
        log.info("进入ReportDetailServiceImpl/getByReportId方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("report_id", reportId);
        queryWrapper.ne("report_type", "1");
        queryWrapper.ne("is_delete", "1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getByReportCode(String reportCode) {
        log.info("进入ReportDetailServiceImpl/getByReportCode方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(reportCode), "report_code", reportCode);
        queryWrapper.ne("report_type", "1");
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getBySameReport(ReportDetail reportDetail) {
        log.info("进入ReportDetailServiceImpl/getBySameReport方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(reportDetail.getReportCode()), "report_code", reportDetail.getReportCode().trim());
        queryWrapper.eq(StringUtils.hasText(reportDetail.getProjectId()), "project_id", reportDetail.getProjectId());
        queryWrapper.ne("report_type", "1");
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getIDBySameReport(ReportDetail reportDetail) {
        log.info("进入ReportDetailServiceImpl/getIDBySameReport方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(reportDetail.getProjectId()), "project_id", reportDetail.getProjectId());
        queryWrapper.eq(StringUtils.hasText(reportDetail.getReportCode()), "report_code", reportDetail.getReportCode().trim());
        queryWrapper.eq(StringUtils.hasText(reportDetail.getReportName()), "report_name", reportDetail.getReportName().trim());
        queryWrapper.eq("report_type", "1");
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getByProjectId(String projectId) {
        log.info("进入ReportDetailServiceImpl/getByProjectId方法");
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(projectId), "project_id", projectId);
        queryWrapper.ne("report_type", "1");
        queryWrapper.ne("is_delete","1");
        queryWrapper.orderByAsc("report_code");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }
    
    @Override
    public int removeByReportId(String reportId) {
        log.info("进入ReportDetailServiceImpl/removeByReportId方法");
        ReportDetail ReportDetail = new ReportDetail();
        UpdateWrapper<ReportDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("report_id", reportId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("is_delete", "1");
        return ReportDetailMapper.update(ReportDetail,updateWrapper);
    }

    @Override
    public int removeBatchByReportIds(List<String> reportIds) {
        log.info("进入ReportDetailServiceImpl/removeBatchByReportIds方法");
        ReportDetail ReportDetail = new ReportDetail();
        try {
            UpdateWrapper<ReportDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ne("is_delete", "1");
            updateWrapper.set("is_delete", "1").in("report_id",reportIds);
            return ReportDetailMapper.update(ReportDetail,updateWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
