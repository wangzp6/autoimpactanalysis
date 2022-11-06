package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import com.example.autoimpactanalysis.entity.ReportStatistics;
import com.example.autoimpactanalysis.service.IReportStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import com.example.autoimpactanalysis.common.Result;


/**
 * @ClassName: ReportStatisticsController
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatistics 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/reportStatistics")
public class ReportStatisticsController {
    
    @Resource
    private IReportStatisticsService reportStatisticsService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入reportStatistics/findAll方法");
        return Result.success(reportStatisticsService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{reportId}")
    public Result findById(@PathVariable Integer reportId) {
        log.info("进入reportStatistics/findById方法");
        return Result.success(reportStatisticsService.getById(reportId));
    }

    //根据报表ID查询
    @GetMapping("/getByReportCode/{reportCode}")
    public Result getByReportCode(@PathVariable String reportCode) {
        log.info("进入reportStatistics/ggetByReportCode方法");
        return Result.success(reportStatisticsService.getByReportCode(reportCode));
    }

    //根据项目ID查询
    @GetMapping("/getByProjectId/{projectId}")
    public Result getByProjectId(@PathVariable String projectId) {
        log.info("进入reportStatistics/getByProjectId方法");
        return Result.success(reportStatisticsService.getByProjectId(projectId));
    }

    //分页查询
    @GetMapping("/findReports")
    public Result findReports(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String reportId,
                              @RequestParam(defaultValue = "") String reportName,
                              @RequestParam(defaultValue = "") String projectId,
                              @RequestParam(defaultValue = "") String projectName,
                              @RequestParam(defaultValue = "") String createTime) {
        log.info("进入reportStatistics/findReports方法");
        QueryWrapper<ReportStatisticsVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportId), "r.report_id", reportId);
        queryWrapper.like(StringUtils.hasText(reportName), "r.report_name", reportName);
        queryWrapper.like(StringUtils.hasText(projectId), "r.project_id", projectId);
        queryWrapper.like(StringUtils.hasText(projectName), "p.project_name", projectName);
        queryWrapper.like(StringUtils.hasText(createTime), "p.create_time", createTime);
        queryWrapper.orderByAsc("r.report_id");
        return Result.success(reportStatisticsService.findReports(pageNum, pageSize, queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody ReportStatistics reportStatistics) {
        log.info("进入reportStatistics/save方法");
        return Result.success(reportStatisticsService.saveOrUpdate(reportStatistics));
    }

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入reportStatistics/delete方法");
        return Result.success(reportStatisticsService.removeById(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("进入reportStatistics/deleteBatch方法");
        return Result.success(reportStatisticsService.removeBatchByIds(ids));
    }

}

