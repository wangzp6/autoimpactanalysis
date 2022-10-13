package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IBroomdatascriptService;
import com.example.autoimpactanalysis.entity.Broomdatascript;

/**
 * @ClassName: BroomdatascriptController
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Broomdatascript 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/broomdatascript")
public class BroomdatascriptController {

    @Resource
    private IBroomdatascriptService broomdatascriptService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入broomdatascript/findAll方法");
        return Result.success(broomdatascriptService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("进入broomdatascript/findById方法");
        return Result.success(broomdatascriptService.getById(id));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String bdcid,
                            @RequestParam(defaultValue = "") String bdctitle,
                            @RequestParam(defaultValue = "") String contentdescription,
                            @RequestParam(defaultValue = "") String broomdatascript) {
        log.info("进入broomdatascript/findPage方法");
        QueryWrapper<Broomdatascript> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(bdcid), "bdcid", bdcid);
        queryWrapper.like(StringUtils.hasText(bdctitle), "bdctitle", bdctitle);
        queryWrapper.like(StringUtils.hasText(contentdescription), "contentdescription", contentdescription);
        queryWrapper.like(StringUtils.hasText(broomdatascript), "broomdatascript", broomdatascript);
        queryWrapper.orderByDesc("bdcid");
//        return Result.success(broomdatascriptService.page(new Page<>(pageNum, pageSize), queryWrapper));

        /*QueryWrapper<Broomdatascript> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(bdcid), "broomdatascript.bdcid", bdcid);
        queryWrapper.like(StringUtils.hasText(bdcid), "sys_files.bdcid", bdcid);
        queryWrapper.like(StringUtils.hasText(bdctitle), "broomdatascript.bdctitle", bdctitle);
        queryWrapper.like(StringUtils.hasText(contentdescription), "broomdatascript.contentdescription", contentdescription);
        queryWrapper.like(StringUtils.hasText(broomdatascript), "broomdatascript.broomdatascript", broomdatascript);
        queryWrapper.apply("broomdatascript.bdcid = sys_files.bdcid");
        queryWrapper.orderByDesc("broomdatascript.bdcid");*/
        return Result.success(broomdatascriptService.findBroomdatascript(pageNum, pageSize, queryWrapper));

    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody Broomdatascript broomdatascript) {
        log.info("进入broomdatascript/save方法");
        return Result.success(broomdatascriptService.saveOrUpdate(broomdatascript));
    }

    /*//根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入broomdatascript/delete方法");
        return Result.success(broomdatascriptService.removeById(id));
    }*/

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入broomdatascript/delete方法");
        return Result.success(broomdatascriptService.removeByIdWithFile(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("进入broomdatascript/deleteBatch方法");
        return Result.success(broomdatascriptService.removeBatchByIds(ids));
    }

}

