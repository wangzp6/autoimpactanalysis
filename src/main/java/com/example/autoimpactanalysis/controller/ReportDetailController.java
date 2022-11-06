package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IReportDetailService;
import com.example.autoimpactanalysis.entity.ReportDetail;


/**
 * @ClassName: ReportDetailController
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: ReportDetail 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/reportDetail")
public class ReportDetailController {

    @Resource
    private IReportDetailService reportDetailService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入reportDetail/findAll方法");
        return Result.success(reportDetailService.findList());
    }

    //根据ID查询
    @GetMapping("/findById/{reportId}")
    public Result findById(@PathVariable Integer reportId) {
        log.info("进入reportDetail/findById方法");
        return Result.success(reportDetailService.getByReportId(reportId));
    }

    //根据报表Code查询
    @GetMapping("/getByReportCode/{reportCode}")
    public Result getByReportCode(@PathVariable String reportCode) {
        log.info("进入reportDetail/getByReportCode方法");
        return Result.success(reportDetailService.getByReportCode(reportCode));
    }

    //根据项目ID查询
    @GetMapping("/getByProjectId/{projectId}")
    public Result getByProjectId(@PathVariable String projectId) {
        log.info("进入reportDetail/getByProjectId方法");
        return Result.success(reportDetailService.getByProjectId(projectId));
    }

    //分页查询
    @GetMapping("/findReports")
    public Result findReports(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String reportCode,
                              @RequestParam(defaultValue = "") String reportName,
                              @RequestParam(defaultValue = "") String projectCode,
                              @RequestParam(defaultValue = "") String projectName) {
        log.info("进入reportDetail/findReports方法");
        QueryWrapper<ReportDetailVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportCode), "r.report_code", reportCode);
        queryWrapper.like(StringUtils.hasText(reportName), "r.report_name", reportName);
        queryWrapper.like(StringUtils.hasText(projectCode), "p.project_code", projectCode);
        queryWrapper.like(StringUtils.hasText(projectName), "p.project_name", projectName);
        queryWrapper.ne("r.is_delete","1");
        queryWrapper.orderByAsc("r.report_id");
        return Result.success(reportDetailService.findReports(pageNum, pageSize, queryWrapper));
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody ReportDetail reportDetail) {
        log.info("进入reportDetail/save方法");

        List<ReportDetail> reportDetails = reportDetailService.getBySameReport(reportDetail);
        if (!reportDetails.isEmpty()) {
            return Result.error("600","此报表已存在！");
        }else{
            reportDetail.setIsDelete("0");
            return Result.success(reportDetailService.saveOrUpdate(reportDetail));
        }
    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody ReportDetail reportDetail) {
        log.info("进入reportDetail/edit方法");
        return Result.success(reportDetailService.saveOrUpdate(reportDetail));
    }

    //根据ID删除
    @DeleteMapping("/delete/{reportId}")
    public Result delete(@PathVariable Integer reportId) {
        log.info("进入reportDetail/delete方法");
        return Result.success(reportDetailService.removeByReportId(reportId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> reportIds) {
        log.info("进入reportDetail/deleteBatch方法");
        return Result.success(reportDetailService.removeBatchByReportIds(reportIds));
    }

}

