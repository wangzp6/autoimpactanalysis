package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;

import java.util.List;

/**
 * @ClassName: IReportDetailService
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: ReportDetail 服务接口
 */
public interface IReportDetailService extends IService<ReportDetail> {
    List<ReportDetail> findList();

    IPage<ReportDetailVO> findReports(Integer pageNum, Integer pageSize, QueryWrapper<ReportDetailVO> queryWrapper);

    List<ReportDetail> getByReportId(String reportId);

    List<ReportDetail> getByReportCode(String reportCode);

    List<ReportDetail> getBySameReport(ReportDetail reportDetail);

    List<ReportDetail> getIDBySameReport(ReportDetail reportDetail);

    List<ReportDetail> getByProjectId(String projectId);

    int removeBatchByReportIds(List<String> reportIds);

    int removeByReportId(String reportId);


}
