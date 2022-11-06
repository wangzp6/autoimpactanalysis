package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IDocumentDetailService;
import com.example.autoimpactanalysis.entity.DocumentDetail;


/**
 * @ClassName: DocumentDetailController
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/documentDetail")
public class DocumentDetailController {

    @Resource
    private IDocumentDetailService documentDetailService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入documentDetail/findAll方法");
        return Result.success(documentDetailService.findList());
    }

    //根据ID查询
    @GetMapping("/findById/{codeId}")
    public Result findById(@PathVariable Integer codeId) {
        log.info("进入documentDetail/findById方法");
        return Result.success(documentDetailService.getByDocId(codeId));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String reportCode,
                           @RequestParam(defaultValue = "") String reportName,
                           @RequestParam(defaultValue = "") String projectCode,
                           @RequestParam(defaultValue = "") String projectName) {
        log.info("进入documentDetail/findPage方法");
        QueryWrapper<DocumentDetailVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(reportCode), "r.report_code", reportCode);
        queryWrapper.like(StringUtils.hasText(reportName), "r.report_name", reportName);
        queryWrapper.like(StringUtils.hasText(projectCode), "p.project_code", projectCode);
        queryWrapper.like(StringUtils.hasText(projectName), "p.project_name", projectName);
        queryWrapper.ne("d.is_delete","1");
        queryWrapper.orderByDesc("doc_id");
        return Result.success(documentDetailService.findPage(pageNum, pageSize, queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody DocumentDetail documentDetail) {
        log.info("进入documentDetail/save方法");
        documentDetail.setDocState("0");
        List<DocumentDetail> documentDetails = documentDetailService.getBySameDoc(documentDetail);
        if (!documentDetails.isEmpty()) {
            return Result.error("600","此文档已存在！");
        }else{
            documentDetail.setIsDelete("0");
            return Result.success(documentDetailService.saveOrUpdate(documentDetail));
        }
    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody DocumentDetail documentDetail) {
        log.info("进入documentDetail/edit方法");
        documentDetail.setUpdateTime(new Date());
        return Result.success(documentDetailService.saveOrUpdate(documentDetail));
    }

    //定稿
    @PostMapping("/editDocState")
    public Result editDocState(@RequestBody DocumentDetail documentDetail) {
        log.info("进入documentDetail/editDocState方法");
        documentDetail.setDocState("1");
        documentDetail.setUpdateTime(new Date());
        return Result.success(documentDetailService.saveOrUpdate(documentDetail));
    }


    //根据ID删除
    @DeleteMapping("/delete/{docId}")
    public Result delete(@PathVariable Integer docId) {
        log.info("进入documentDetail/delete方法");
        return Result.success(documentDetailService.removeByDocId(docId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> docIds) {
        log.info("进入documentDetail/deleteBatch方法");
        return Result.success(documentDetailService.removeBatchByDocIds(docIds));
    }


}

