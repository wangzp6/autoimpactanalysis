package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.service.IProjectDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;

/**
 * @ClassName: projectDetailController
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: projectDetail 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/projectDetail")
public class ProjectDetailController {
    @Resource
    private IProjectDetailService projectDetailService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入projectDetail/findAll方法");
        return Result.success(projectDetailService.findList());
    }

    //根据projectId查询
    @GetMapping("/findByProjectId/{projectId}")
    public Result findByProjectId(@PathVariable String projectId) {
        log.info("进入projectDetail/findByProjectId方法");
        return Result.success(projectDetailService.getByProjectId(projectId));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String projectCode,
                           @RequestParam(defaultValue = "") String projectName,
                           @RequestParam(defaultValue = "") String createTime,
                           @RequestParam(defaultValue = "") String putIntoProTime) {
        log.info("进入projectDetail/findPage方法");
        QueryWrapper<ProjectDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(projectCode), "project_code", projectCode);
        queryWrapper.like(StringUtils.hasText(projectName), "project_name", projectName);
        queryWrapper.like(StringUtils.hasText(createTime), "create_time", createTime);
        queryWrapper.like(StringUtils.hasText(putIntoProTime), "put_into_pro_time", putIntoProTime);
        queryWrapper.ne("is_delete", "1");
        queryWrapper.orderByAsc("project_code");
        return Result.success(projectDetailService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody ProjectDetail projectDetail) {
        log.info("进入projectDetail/save方法");
        List<ProjectDetail> projectDetails = projectDetailService.getByProjectCode(projectDetail.getProjectCode());
        if (!projectDetails.isEmpty()) {
            return Result.error("600","此项目已存在！");
        }else{
            projectDetail.setIsDelete("0");
            return Result.success(projectDetailService.saveOrUpdate(projectDetail));
        }
    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody ProjectDetail projectDetail) {
        log.info("进入projectDetail/edit方法");
        return Result.success(projectDetailService.saveOrUpdate(projectDetail));
    }

    //根据ID删除
    @DeleteMapping("/delete/{projectId}")
    public Result delete(@PathVariable String projectId) {
        log.info("进入projectDetail/delete方法");
        return Result.success(projectDetailService.removeByProjectId(projectId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<String> projectIds) {
        log.info("进入projectDetail/deleteBatch方法");
        return Result.success(projectDetailService.removeBatchByProjectIds(projectIds));
    }

}

