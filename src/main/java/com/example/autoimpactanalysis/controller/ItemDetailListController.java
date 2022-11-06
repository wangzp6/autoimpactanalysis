package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.entity.VO.ItemDetailListVO;
import com.example.autoimpactanalysis.entity.VO.ItemDetailVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IItemDetailListService;
import com.example.autoimpactanalysis.entity.ItemDetailList;


/**
 * @ClassName: ItemDetailListController
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description: ItemDetailList 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/itemDetailList")
public class ItemDetailListController {

    @Resource
    private IItemDetailListService itemDetailListService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入itemDetailList/findAll方法");
        return Result.success(itemDetailListService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("进入itemDetailList/findById方法");
        return Result.success(itemDetailListService.getById(id));
    }

    @GetMapping("/getItemByReportId/{reportId}")
    public Result getItemDetailListByReportId(@PathVariable String reportId) {
        log.info("进入itemDetailList/getItemByReportId方法");
        return Result.success(itemDetailListService.getItemDetailListByReportId(reportId));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody ItemDetailListVO itemDetailListVO) {
        log.info("进入itemDetailList/save方法");
        return Result.success(itemDetailListService.saveOrUpdateItem(itemDetailListVO));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String reportId,
                           @RequestParam(defaultValue = "") String reportName) {
        log.info("进入itemDetailList/findPage方法");
        QueryWrapper<ItemDetailList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportId), "i.report_id", reportId);
        queryWrapper.like(StringUtils.hasText(reportName), "r.report_name", reportName);
        queryWrapper.orderByDesc("i.report_id");
        return Result.success(itemDetailListService.findPage(pageNum, pageSize, queryWrapper));
    }

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入itemDetailList/delete方法");
        return Result.success(itemDetailListService.removeById(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("进入itemDetailList/deleteBatch方法");
        return Result.success(itemDetailListService.removeBatchByIds(ids));
    }

}

