package com.example.autoimpactanalysis.controller;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.utils.SvnKitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

/**
 * @ClassName: SVNController
 * @Author: kevin
 * @Date: 2022-07-06
 * @Version: V1.0
 * @Description: SVN 前端控制器
 */
@RestController
@RequestMapping("/svn")
public class SVNController {
    private static Logger logger = LoggerFactory.getLogger(SourceController.class);

    //上传
    @PostMapping("/upload")
    public Result upload(String filename,String filepath) {
        logger.info("进入svn/upload方法");
        try {
//            String filename = "354.txt";
            String workspace = "D:/workspace/newIdea_workspace/autoimpactanalysis/files";
//            String filepath = "/file/2";
            SvnKitUtils svnDeal = new SvnKitUtils();
            svnDeal.upload(workspace,filepath, filename,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    //确定一个URL在SVN上是否存在
    @PostMapping("/isexist")
    public Result isexist(String filefullname) {
        logger.info("进入svn/isexist方法");
        Boolean result = false;
        try {
            SvnKitUtils svnDeal = new SvnKitUtils();
            SVNURL svnurl = SVNURL.parseURIEncoded(filefullname);
            logger.info("host:"+svnurl.getHost());
            logger.info("path:"+svnurl.getPath());
            logger.info("protocol:"+svnurl.getProtocol());
            logger.info("URIEncodedPath:"+svnurl.getURIEncodedPath());
            logger.info("UserInfo:"+svnurl.getUserInfo());
            logger.info("Port:"+svnurl.getPort());
            result = svnDeal.isURLExist(svnurl);
            logger.info("执行结果："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(result);
    }


    //验证登录svn
    @PostMapping("/authSvn")
    public Result authSvn(String filefullname) {
        logger.info("进入svn/authSvn方法");
        try {
            SvnKitUtils svnDeal = new SvnKitUtils();
            svnDeal.authSvn(filefullname);
            logger.info("执行完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @GetMapping("getList")
    public Result getList(String filepath) {
        logger.info("进入svn/getList方法");
        try {
            SvnKitUtils svnDeal = new SvnKitUtils();
//            SVNURL svnurl = SVNURL.parseURIEncoded(filepath);
            SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(filepath));
            svnDeal.listEntries(repository,filepath);
            logger.info("执行完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
