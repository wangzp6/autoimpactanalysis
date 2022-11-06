package com.example.autoimpactanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import com.example.autoimpactanalysis.mapper.ReportDetailMapper;
import com.example.autoimpactanalysis.service.IReportDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
public class ReportDetailServiceImpl extends ServiceImpl<ReportDetailMapper, ReportDetail> implements IReportDetailService {

    @Autowired
    private ReportDetailMapper ReportDetailMapper;

    @Override
    public List<ReportDetail> findList() {
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("is_delete", "1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public IPage<ReportDetailVO> findReports(Integer pageNum, Integer pageSize, QueryWrapper<ReportDetailVO> queryWrapper) {
        IPage<ReportDetailVO> ReportDetailIPage = ReportDetailMapper.findReports(new Page<>(pageNum, pageSize), queryWrapper);
        return ReportDetailIPage;
    }

    @Override
    public List<ReportDetail> getByReportId(Integer reportId) {
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("report_id", reportId);
        queryWrapper.ne("is_delete", "1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getByReportCode(String reportCode) {
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportCode), "report_code", reportCode);
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getBySameReport(ReportDetail reportDetail) {
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportDetail.getReportCode()), "report_code", reportDetail.getReportCode());
        queryWrapper.like(StringUtils.hasText(reportDetail.getProjectId()), "project_id", reportDetail.getProjectId());
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<ReportDetail> getByProjectId(String projectId) {
        QueryWrapper<ReportDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(projectId), "project_id", projectId);
        queryWrapper.ne("is_delete","1");
        List<ReportDetail> list = list(queryWrapper);
        return list;
    }
    
    @Override
    public int removeByReportId(Integer reportId) {
        ReportDetail ReportDetail = new ReportDetail();
        UpdateWrapper<ReportDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("report_id", reportId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("is_delete", "1");
        return ReportDetailMapper.update(ReportDetail,updateWrapper);
    }

    @Override
    public int removeBatchByReportIds(List<Integer> reportIds) {
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
