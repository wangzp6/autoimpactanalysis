package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.example.autoimpactanalysis.entity.VO.BusConfirmVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IBusConfirmService;
import com.example.autoimpactanalysis.entity.BusConfirm;


/**
 * @ClassName: BusConfirmController
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: BusConfirm 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/busConfirm")
public class BusConfirmController {

    @Resource
    private IBusConfirmService busConfirmService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入busConfirm/findAll方法");
        return Result.success(busConfirmService.findList());
    }

    //根据ID查询
    @GetMapping("/findById/{busConfirmId}")
    public Result findById(@PathVariable Integer busConfirmId) {
        log.info("进入busConfirm/findById方法");
        return Result.success(busConfirmService.getBybBusConfirmId(busConfirmId));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String busConfirmQuestion,
                            @RequestParam(defaultValue = "") String busConfirmState,
                            @RequestParam(defaultValue = "") String busConfirmTime,
                            @RequestParam(defaultValue = "") String questioner,
                            @RequestParam(defaultValue = "") String replier,
                            @RequestParam(defaultValue = "") String reportId,
                            @RequestParam(defaultValue = "") String reportName,
                            @RequestParam(defaultValue = "") String projectId,
                            @RequestParam(defaultValue = "") String projectName) {
        log.info("进入busConfirm/findPage方法");
        QueryWrapper<BusConfirmVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(busConfirmQuestion), "b.bus_confirm_question", busConfirmQuestion);
        queryWrapper.like(StringUtils.hasText(busConfirmState), "b.bus_confirm_state", busConfirmState);
        queryWrapper.like(StringUtils.hasText(busConfirmTime), "b.bus_confirm_time", busConfirmTime);
        queryWrapper.like(StringUtils.hasText(questioner), "b.questioner", questioner);
        queryWrapper.like(StringUtils.hasText(replier), "b.replier", replier);
        queryWrapper.like(StringUtils.hasText(reportId), "r.report_id", reportId);
        queryWrapper.like(StringUtils.hasText(reportName), "r.report_name", reportName);
        queryWrapper.like(StringUtils.hasText(projectId), "p.project_id", projectId);
        queryWrapper.like(StringUtils.hasText(projectName), "p.project_name", projectName);
        queryWrapper.ne("b.is_delete","1");
        queryWrapper.orderByDesc("b.bus_confirm_id");
        return Result.success(busConfirmService.findBusConfirm(pageNum, pageSize, queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody BusConfirm busConfirm) {
        log.info("进入busConfirm/save方法");
        busConfirm.setBusConfirmState("0");
        busConfirm.setIsDelete("0");
        return Result.success(busConfirmService.saveOrUpdate(busConfirm));

    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody BusConfirm busConfirm) {
        log.info("进入busConfirm/edit方法");
        busConfirm.setUpdateTime(new Date());
        return Result.success(busConfirmService.saveOrUpdate(busConfirm));
    }

    //定稿
    @PostMapping("/editConfirmState")
    public Result editConfirmState(@RequestBody BusConfirm busConfirm) {
        log.info("进入busConfirm/editConfirmState方法");
        busConfirm.setBusConfirmState("1");
        busConfirm.setUpdateTime(new Date());
        return Result.success(busConfirmService.saveOrUpdate(busConfirm));
    }

    //根据ID删除
    @DeleteMapping("/delete/{busConfirmId}")
    public Result delete(@PathVariable Integer busConfirmId) {
        log.info("进入busConfirm/delete方法");
        return Result.success(busConfirmService.removeByBusConfirmId(busConfirmId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> busConfirmIds) {
        log.info("进入busConfirm/deleteBatch方法");
        return Result.success(busConfirmService.removeBatchByBusConfirmIds(busConfirmIds));
    }

}

