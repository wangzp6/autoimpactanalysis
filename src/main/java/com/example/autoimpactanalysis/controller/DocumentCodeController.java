package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.IDocumentCodeService;
import com.example.autoimpactanalysis.entity.DocumentCode;


/**
 * @ClassName: DocumentCodeController
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentCode 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/documentCode")
public class DocumentCodeController {

    @Resource
    private IDocumentCodeService documentCodeService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入documentCode/findAll方法");
        return Result.success(documentCodeService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{codeId}")
    public Result findById(@PathVariable String codeId) {
        log.info("进入documentCode/findById方法");
        return Result.success(documentCodeService.getById(codeId));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String docCode,
                            @RequestParam(defaultValue = "") String docType) {
        log.info("进入documentCode/findPage方法");
        QueryWrapper<DocumentCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(docCode), "doc_code", docCode);
        queryWrapper.like(StringUtils.hasText(docType), "doc_type", docType);
        queryWrapper.orderByDesc("doc_code");
        return Result.success(documentCodeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody DocumentCode documentCode) {
        log.info("进入documentCode/save方法");
        documentCode.setDocCode(documentCode.getDocCode().trim());
        documentCode.setDocType(documentCode.getDocType().trim());
        List<DocumentCode> documentCodes= documentCodeService.getByDocCode(documentCode.getDocCode());
        if(!documentCodes.isEmpty()){
            return Result.success("此文档类型已存在！");
        }else{
            return Result.success(documentCodeService.saveOrUpdate(documentCode));
        }
    }

    //修改
    @PostMapping("/edit")
    public Result edit(@RequestBody DocumentCode documentCode) {
        log.info("进入documentCode/edit方法");
        documentCode.setDocCode(documentCode.getDocCode().trim());
        documentCode.setDocType(documentCode.getDocType().trim());
        List<DocumentCode> documentCodes= documentCodeService.getByDocCode(documentCode.getDocCode());
        if(!documentCodes.isEmpty()){
            return Result.success("此文档类型已存在！");
        }else{
            return Result.success(documentCodeService.saveOrUpdate(documentCode));
        }
    }


    //根据ID删除
    @DeleteMapping("/delete/{codeId}")
    public Result delete(@PathVariable String codeId) {
        log.info("进入documentCode/delete方法");
        return Result.success(documentCodeService.removeById(codeId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<String> codeIds) {
        log.info("进入documentCode/deleteBatch方法");
        return Result.success(documentCodeService.removeBatchByIds(codeIds));
    }

}

