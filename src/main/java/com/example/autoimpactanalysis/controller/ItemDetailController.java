package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.VO.ItemDetailVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IItemDetailService;
import com.example.autoimpactanalysis.entity.ItemDetail;


/**
 * @ClassName: ItemDetailController
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description: ItemDetail 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/itemDetail")
public class ItemDetailController {

    @Resource
    private IItemDetailService itemDetailService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入itemDetail/findAll方法");
        return Result.success(itemDetailService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable String id) {
        log.info("进入itemDetail/findById方法");
        return Result.success(itemDetailService.getById(id));
    }

    @GetMapping("/getItemByReportId/{reportId}")
    public Result getItemDetailListByReportId(@PathVariable String reportId) {
        ItemDetailVO itemDetailVO = new ItemDetailVO();
        itemDetailVO.setReportId(reportId);
        itemDetailVO.setItemParentId("0");
        List<ItemDetailVO> itemDetailVOList = itemDetailService.getItemDetailListByReportId(itemDetailVO);
        return Result.success(itemDetailVOList);
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody ItemDetail itemDetail) {
        log.info("进入itemDetail/save方法");
        return Result.success(itemDetailService.saveOrUpdate(itemDetail));
    }

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        log.info("进入itemDetail/delete方法");
        return Result.success(itemDetailService.removeById(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<String> ids) {
        log.info("进入itemDetail/deleteBatch方法");
        return Result.success(itemDetailService.removeBatchByIds(ids));
    }

}

