package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import com.example.autoimpactanalysis.service.IReportDetailService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Result findById(@PathVariable String codeId) {
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
        queryWrapper.ne("d.is_delete", "1");
        queryWrapper.orderByDesc("doc_id");
        return Result.success(documentDetailService.findPage(pageNum, pageSize, queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody DocumentDetailVO documentDetailVO) {
        log.info("进入documentDetail/save方法");
        documentDetailVO.setDocState("0");
        List<DocumentDetail> documentDetails = documentDetailService.getBySameDocVO(documentDetailVO);
        if (!documentDetails.isEmpty()) {
            return Result.error("600", "此文档已存在！");
        } else {
            documentDetailVO.setUpdateTime(new Date());
            documentDetailVO.setIsDelete("0");
            return Result.success(documentDetailService.saveOrUpdate(documentDetailVO));
        }
    }

    @Resource
    private IReportDetailService reportDetailService;

    //新增非报表章节
    @PostMapping("/saveChapter")
    public Result saveChapter(@RequestBody DocumentDetailVO documentDetailVO) {
        log.info("进入documentDetail/saveChapter方法");
        //查询是否同项目存在同状态重复标题
        if (StringUtils.hasText(documentDetailVO.getReportName())) {
            List<DocumentDetailVO> documentDetailVOS = documentDetailService.getBySameChapter(documentDetailVO);
            if (!documentDetailVOS.isEmpty()) {
                return Result.error("600", "此项目下已存在同名章节！");
            } else {
                //新增默认报表
                ReportDetail reportDetail = new ReportDetail();
                reportDetail.setReportName(documentDetailVO.getReportName().trim());
                reportDetail.setProjectId(documentDetailVO.getProjectId());
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
                reportDetail.setReportCode("C" + df.format(new Date()));
                reportDetail.setReportType("1");
                reportDetail.setIsDelete("0");
                if (reportDetailService.saveOrUpdate(reportDetail)) {
                    log.info("新增报表成功");
                    List<ReportDetail> reportDetails = reportDetailService.getIDBySameReport(reportDetail);
                    if (reportDetails.size() == 1) {
                        //新增文档
                        documentDetailVO.setReportId(reportDetails.get(0).getReportId());
                        documentDetailVO.setUpdateTime(new Date());
                        documentDetailVO.setIsDelete("0");
                        return Result.success(documentDetailService.saveOrUpdate(documentDetailVO));
                    } else {
                        return Result.error("600", "章节重复！");
                    }
                } else {
                    return Result.error("600", "新增章节失败！");
                }
            }
        }
        return Result.error("600", "章节标题为空！");
    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody DocumentDetail documentDetail) {
        log.info("进入documentDetail/edit方法");
        documentDetail.setUpdateTime(new Date());
        return Result.success(documentDetailService.saveOrUpdate(documentDetail));
    }

    //修改非报表章节
    @PostMapping("/editChapter")
    public Result editChapter(@RequestBody DocumentDetailVO documentDetailVO) {
        log.info("进入documentDetail/editChapter方法");
        //查询是否同项目存在同状态重复标题
        if (StringUtils.hasText(documentDetailVO.getReportName())) {
            List<DocumentDetailVO> documentDetailVOS = documentDetailService.getBySameChapter(documentDetailVO);
            if (!documentDetailVOS.isEmpty()&&!documentDetailVO.getDocId().equals(documentDetailVOS.get(0).getDocId())) {
                return Result.error("600", "此项目下已存在同名章节！");
            } else {
                //修改报表
                ReportDetail reportDetail = new ReportDetail();
                reportDetail.setReportId(documentDetailVO.getReportId());
                reportDetail.setReportName(documentDetailVO.getReportName());
                reportDetail.setProjectId(documentDetailVO.getProjectId());
                if (reportDetailService.saveOrUpdate(reportDetail)) {
                    log.info("修改报表成功");
                    //修改文档
                    documentDetailVO.setUpdateTime(new Date());
                    return Result.success(documentDetailService.saveOrUpdate(documentDetailVO));
                } else {
                    return Result.error("600", "修改章节失败！");
                }
            }
        }
        return Result.error("600", "章节标题为空！");
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
    @GetMapping("/delete/")
    public Result delete(@RequestParam String docId,
                         @RequestParam String operator) {
        log.info("进入documentDetail/delete方法");
        DocumentDetailVO documentDetailVO = documentDetailService.getDocVOByDocId(docId);
        if ("1".equals(documentDetailVO.getReportType())) {
            ReportDetail reportDetail = new ReportDetail();
            reportDetail.setReportId(documentDetailVO.getReportId());
            reportDetail.setIsDelete("1");
            reportDetailService.saveOrUpdate(reportDetail);
        }
        return Result.success(documentDetailService.removeByDocId(docId, operator));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<DocumentDetail> docs) {
        log.info("进入documentDetail/deleteBatch方法");
        List<String> reportIds = new ArrayList<>();
        for (DocumentDetail d : docs) {
            DocumentDetailVO documentDetailVO = documentDetailService.getDocVOByDocId(d.getDocId());
            if("1".equals(documentDetailVO.getReportType())){
                reportIds.add(documentDetailVO.getReportId());
            }
        }
        if(reportIds.size()>0){
            reportDetailService.removeBatchByReportIds(reportIds);
        }
        return Result.success(documentDetailService.removeBatchByDocIds(docs));
    }


}

