package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.example.autoimpactanalysis.service.IBroomDataScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import com.example.autoimpactanalysis.common.Result;

/**
 * @ClassName: BroomDataScriptController
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScript 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/broomDataScript")
public class BroomDataScriptController {
    
    @Resource
    private IBroomDataScriptService broomDataScriptService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入broomDataScript/findAll方法");
        return Result.success(broomDataScriptService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("进入broomDataScript/findById方法");
        return Result.success(broomDataScriptService.getById(id));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String bdcNumber,
                            @RequestParam(defaultValue = "") String bdcTitle,
                            @RequestParam(defaultValue = "") String principal,
                            @RequestParam(defaultValue = "") String contentDescription,
                            @RequestParam(defaultValue = "") String broomDataScript) {
        log.info("进入broomDataScript/findPage方法");
        QueryWrapper<BroomDataScript> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(bdcNumber), "bdc_number", bdcNumber);
        queryWrapper.like(StringUtils.hasText(bdcTitle), "bdc_title", bdcTitle);
        queryWrapper.like(StringUtils.hasText(principal), "principal", principal);
        queryWrapper.like(StringUtils.hasText(contentDescription), "content_description", contentDescription);
        queryWrapper.like(StringUtils.hasText(broomDataScript), "broom_data_script", broomDataScript);
        queryWrapper.orderByDesc("bdc_number");
//        return Result.success(broomDataScriptService.page(new Page<>(pageNum, pageSize), queryWrapper));

        /*QueryWrapper<Broomdatascript> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(bdcid), "broomdatascript.bdcid", bdcid);
        queryWrapper.like(StringUtils.hasText(bdcid), "sys_files.bdcid", bdcid);
        queryWrapper.like(StringUtils.hasText(bdctitle), "broomdatascript.bdctitle", bdctitle);
        queryWrapper.like(StringUtils.hasText(contentdescription), "broomdatascript.contentdescription", contentdescription);
        queryWrapper.like(StringUtils.hasText(broomdatascript), "broomdatascript.broomdatascript", broomdatascript);
        queryWrapper.apply("broomdatascript.bdcid = sys_files.bdcid");
        queryWrapper.orderByDesc("broomdatascript.bdcid");*/
        return Result.success(broomDataScriptService.findBroomDataScript(pageNum, pageSize, queryWrapper));

    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody BroomDataScript broomdatascript) {
        log.info("进入broomDataScript/save方法");
        return Result.success(broomDataScriptService.saveOrUpdate(broomdatascript));
    }

    /*//根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入broomDataScript/delete方法");
        return Result.success(broomDataScriptService.removeById(id));
    }*/

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入broomDataScript/delete方法");
        return Result.success(broomDataScriptService.removeByIdWithFile(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("进入broomDataScript/deleteBatch方法");
        return Result.success(broomDataScriptService.removeBatchByIds(ids));
    }

}

