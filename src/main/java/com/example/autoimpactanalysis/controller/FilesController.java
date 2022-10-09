package com.example.autoimpactanalysis.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.entity.Broomdatascript;
import com.example.autoimpactanalysis.entity.Files;
import com.example.autoimpactanalysis.mapper.FilesMapper;
import com.example.autoimpactanalysis.service.IBroomdatascriptService;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FilesController
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Files 前端控制器
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    private static Logger log = LoggerFactory.getLogger(FilesController.class);

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private IBroomdatascriptService broomdatascriptService;

    /**
     * @author: kevin
     * @param: file 前端传过来的文件
     * @return Type:
     * @date: 2022/4/7 10:29
     * @describe: 文件上传接口
     */

    /*@PostMapping("/uploadFiles")
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
            url = filesDB.getUrl();
            uploadFile.delete();
        } else {
            url = "http://localhost:9080/autoimpactanalysis/files/downloadFiles/" + fileUUID;
        }

        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        filesMapper.insert(saveFiles);
        return url;
    }*/

    @PostMapping("/uploadFilesWithBdcid/{bcdid}")
    public String uploadFilesWithBdcid(@RequestParam MultipartFile file,@PathVariable Integer bcdid) throws IOException {
        log.info("进入files/uploadFilesWithBdcid方法,bcdid为:"+bcdid);
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
            url = filesDB.getUrl();
            uploadFile.delete();
        } else {
            url = "http://localhost:9080/autoimpactanalysis/files/downloadFiles/" + fileUUID;
        }

        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size);
        saveFiles.setUrl(url);
        saveFiles.setBdcid(bcdid);
        saveFiles.setMd5(md5);
        filesMapper.insert(saveFiles);
        Broomdatascript broomdatascript = broomdatascriptService.getById(bcdid);
        if(broomdatascript.getAttachmentliststr()==null){
            broomdatascript.setAttachmentliststr(originalFilename+";");
        }else{
            broomdatascript.setAttachmentliststr(broomdatascript.getAttachmentliststr()+originalFilename+";");
        }
        broomdatascriptService.updateById(broomdatascript);
        return url;
    }

    /**
     * @author: kevin
     * @param: fileUUID 文件标识码
     * @return Type:
     * @date: 2022/4/8 10:42
     * @describe: 文件下载接口 http://localhost:9080/autoimpactanalysis/files/downloadFiles/{fileUUID}
     */
/*    @GetMapping("downloadFiles/{fileUUID}")
    public void downloadFiles(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        log.info("进入files/downloadFiles方法");
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }*/

    /**
     * @author: kevin
     * @param: fileUUID 文件标识码
     * @return Type:
     * @date: 2022/4/8 10:42
     * @describe: 文件下载接口 http://localhost:9080/autoimpactanalysis/files/downloadFiles/{fileUUID}
     */
    @GetMapping("downloadFiles/{fileUUID}")
    public void downloadFiles(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        log.info("进入files/downloadFiles方法,fileUUID:"+fileUUID);
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        if(!uploadFile.exists()){
            log.info("文件不存在："+fileUploadPath + fileUUID);
        }
            //设置输出流的格式
            ServletOutputStream os = response.getOutputStream();
            ArrayList<Files> files = filesMapper.findByUrl(fileUUID);
        if(files.size()==0){
            log.info("未在数据库查到该文件记录，fileUUID："+fileUUID);
        }
            String filename =files.get(0).getName();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + UrlUtil.encode(filename, "UTF-8"));
            response.setContentType("application/octet-stream");
            //读取文件字节流
            os.write(FileUtil.readBytes(uploadFile));
            os.flush();
            os.close();
    }

    /**
     * @author: kevin
     * @param: null
     * @return Type:
     * @date: 2022/4/21 13:48
     * @describe: 通过文件的md5查询文件
     */
    private Files getFileMD5(String md5) {
        log.info("进入files/getFileMD5方法");
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filelist = filesMapper.selectList(queryWrapper);
        return filelist.size() == 0 ? null : filelist.get(0);
    }

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
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");

        return Result.success(filesMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /*//删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入files/delete方法");
        Files files = filesMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(filesMapper.updateById(files));
    }*/

    //删除
    @DeleteMapping("/deleteWithBdcid/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("进入files/deleteWithBdcid方法");
        Files files = filesMapper.selectById(id);
        files.setIsDelete(true);
        Broomdatascript broomdatascript = broomdatascriptService.getById(files.getBdcid());
        String[] strArr = broomdatascript.getAttachmentliststr().split(files.getName()+";");
        String fileNameListStr = "";
        for (int i = 0; i < strArr.length; i++) {
            fileNameListStr = fileNameListStr + strArr[i];
        }
        broomdatascript.setAttachmentliststr(fileNameListStr);
        broomdatascriptService.updateById(broomdatascript);
        return Result.success(filesMapper.updateById(files));

    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        log.info("进入files/deleteBatch方法");
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //select * from sys_file where id in()
        queryWrapper.in("id",ids);
        List<Files> files = filesMapper.selectList(queryWrapper);
        int len=0;
        for(Files files1:files){
            files1.setIsDelete(true);
            int i = filesMapper.updateById(files1);
            len = len + i;
        }
        return Result.success(len);
    }

    //修改
    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        log.info("进入files/save方法");
        return Result.success(filesMapper.updateById(files));
    }
}

