package com.example.autoimpactanalysis.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.example.autoimpactanalysis.entity.Files;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.example.autoimpactanalysis.mapper.FilesMapper;
import com.example.autoimpactanalysis.service.IBroomDataScriptService;
import com.example.autoimpactanalysis.service.IReportDetailService;
import com.example.autoimpactanalysis.utils.OfficeUtil;
import com.sun.jndi.toolkit.url.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: FilesController
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Files 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {

    private String basePath = System.getProperty("user.dir") + "//";

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${files.download.path}")
    private String fileDownloadPath;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private IBroomDataScriptService broomdatascriptService;

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        log.info("进入files/findPage方法");
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        //查询未删除的记录
        queryWrapper.ne("is_delete", "1");
        queryWrapper.orderByDesc("file_id");

        return Result.success(filesMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * @return Type:
     * @author: kevin
     * @param: file 前端传过来的文件
     * @date: 2022/4/7 10:29
     * @describe: 文件上传接口
     */
    @PostMapping("/uploadFiles")
    public String uploadFiles(@RequestParam MultipartFile file) throws IOException {
        log.info("进入files/uploadFiles方法");
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //单位是字节（b）,若需要Kb可 size/1024
        long size = file.getSize();

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);

        Files filesDB = getFileMD5(md5);
        //获取文件
        String url;
        if (filesDB != null) {
            url = filesDB.getFileUrl();
            uploadFile.delete();
        } else {
            url = "http://localhost:9080/autoimpactanalysis/files/downloadFiles/" + fileUUID;
        }

        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setFileName(originalFilename);
        saveFiles.setFileType(type);
        saveFiles.setFileSize(size);
        saveFiles.setFileUrl(url);
        saveFiles.setFileMd5(md5);
        filesMapper.insert(saveFiles);
        return url;
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        log.info("进入files/update方法");
        return Result.success(filesMapper.updateById(files));
    }

    //删除
    @DeleteMapping("/deleteByFileId/{fileId}")
    public Result deleteByFileId(@PathVariable String fileId) {
        log.info("进入files/deleteByFileId方法");
        Files files = filesMapper.selectById(fileId);
        files.setIsDelete("1");
        return Result.success(filesMapper.updateById(files));
    }

    //批量删除
    @PostMapping("/deleteBatchByFileId")
    public Result deleteBatchByFileId(@RequestBody List<String> fileIds) {
        log.info("进入files/deleteBatchByFileId方法");
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //select * from sys_file where id in()
        queryWrapper.in("file_id", fileIds);
        List<Files> files = filesMapper.selectList(queryWrapper);
        int len = 0;
        for (Files files1 : files) {
            files1.setIsDelete("1");
            int i = filesMapper.updateById(files1);
            len = len + i;
        }
        return Result.success(len);
    }

    /**
     * @return Type:
     * @author: kevin
     * @param: fileUUID 文件标识码
     * @date: 2022/4/8 10:42
     * @describe: 文件下载接口 http://autoimpactanalysis.com.cn:9080/autoimpactanalysis/files/downloadFiles/{fileUUID}
     */
    @GetMapping("downloadFiles/{fileUUID}")
    public void downloadFiles(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        log.info("进入files/downloadFiles方法,fileUUID:" + fileUUID);
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(basePath + fileUploadPath + fileUUID);
        if (!uploadFile.exists()) {
            log.info("文件不存在：" + basePath + fileUploadPath + fileUUID);
        }
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        ArrayList<Files> files = filesMapper.findByUrl(fileUUID);
        if (files.size() == 0) {
            log.info("未在数据库查到该文件记录，fileUUID：" + fileUUID);
        }
        String fileName = files.get(0).getFileName();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + UrlUtil.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * @return Type:
     * @author: kevin
     * @param: null
     * @date: 2022/4/21 13:48
     * @describe: 通过文件的md5查询文件
     */
    private Files getFileMD5(String fileMd5) {
        log.info("进入files/getFileMD5方法");
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_md5", fileMd5);
        List<Files> filelist = filesMapper.selectList(queryWrapper);
        return filelist.size() == 0 ? null : filelist.get(0);
    }

    @PostMapping("/uploadFilesWithBdcId/{bdcId}")
    public String uploadFilesWithBdcid(@RequestParam MultipartFile file, @PathVariable String bdcId) throws IOException {
        log.info("进入files/uploadFilesWithBdcid方法,bdcId为:" + bdcId);
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //单位是字节（b）,若需要Kb可 size/1024
        long size = file.getSize();

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(basePath + fileUploadPath + fileUUID);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件的md5
        String fileMd5 = SecureUtil.md5(uploadFile);

        Files filesDB = getFileMD5(fileMd5);
        //获取文件
        String url;
        if (filesDB != null) {
            url = filesDB.getFileUrl();
            uploadFile.delete();
        } else {
            url = "http://autoimpactanalysis.com.cn:9080/autoimpactanalysis/files/downloadFiles/" + fileUUID;
        }

        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setFileName(originalFilename);
        saveFiles.setFileType(type);
        saveFiles.setFileSize(size);
        saveFiles.setFileUrl(url);
        saveFiles.setBdcId(bdcId);
        saveFiles.setFileMd5(fileMd5);
        saveFiles.setEnable("1");
        saveFiles.setIsDelete("0");
        filesMapper.insert(saveFiles);
        BroomDataScript broomdatascript = broomdatascriptService.getById(bdcId);
        if (broomdatascript.getAttachmentListStr() == null) {
            broomdatascript.setAttachmentListStr(originalFilename + ";");
        } else {
            broomdatascript.setAttachmentListStr(broomdatascript.getAttachmentListStr() + originalFilename + ";");
        }
        broomdatascriptService.updateById(broomdatascript);
        return url;
    }

    //删除
    @DeleteMapping("/deleteWithBdcId/{bdcId}")
    public Result delete(@PathVariable String bdcId) {
        log.info("进入files/deleteWithBdcId方法");
        Files files = filesMapper.selectById(bdcId);

        files.setIsDelete("1");
        BroomDataScript broomdatascript = broomdatascriptService.getById(files.getBdcId());
        String[] strArr = broomdatascript.getAttachmentListStr().split(files.getFileName() + ";");
        log.info(strArr.toString());
        String fileNameListStr = "";
        for (int i = 0; i < strArr.length; i++) {
            fileNameListStr = fileNameListStr + strArr[i];
        }

        log.info(fileNameListStr.toString());
        broomdatascript.setAttachmentListStr(fileNameListStr.toString());
        broomdatascriptService.updateById(broomdatascript);
        return Result.success(filesMapper.updateById(files));

    }

    @Resource
    private IReportDetailService reportDetailService;

    @GetMapping("/downloadWord/{reportIds}")
    public void downloadWord(@PathVariable List<String> reportIds, HttpServletResponse response) throws Exception {
        log.info("进入files/downloadWord方法:reportIds:" + reportIds);

        StringBuffer reportDetails = new StringBuffer();
        String fileName;
        String reportIdNo1 = null;
        String reportNameNo1 = null;

        for (int i = 0; i < reportIds.size(); i++) {
            ReportDetail reportDetail = reportDetailService.getById(reportIds.get(i));
            String reportId = reportDetail.getReportId();
            String reportName = reportDetail.getReportName();
            if (i == 0) {
                reportIdNo1 = reportId;
                reportNameNo1 = reportName;
            }

//            String reportDetailStr = reportDetail.getReportDetail();
//            reportDetails = reportDetails.append(reportDetail);
        }

        if (reportIds.size() > 1) {
            log.info("多报表操作");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String today = format.format(date);
            fileName = "报表需求" + today + ".doc";
        } else {
            log.info("单报表操作");
            fileName = reportIdNo1 + reportNameNo1 + ".doc";
        }

        File uploadFile = new File(basePath + fileDownloadPath + fileName);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        String htmlbody = reportDetails.toString();
        log.info("htmlbody:" + htmlbody);
        File wordFile = OfficeUtil.createWordByHtml(htmlbody, basePath + fileDownloadPath + fileName);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();

        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + UrlUtil.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(wordFile));
        os.flush();
        os.close();

    }
}

