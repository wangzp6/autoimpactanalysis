package com.example.autoimpactanalysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.VO.SourceVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.service.ISourceService;
import com.example.autoimpactanalysis.entity.Source;


/**
 * @ClassName: SourceController
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Source 前端控制器
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    private static Logger logger = LoggerFactory.getLogger(SourceController.class);

    @Resource
    private ISourceService sourceService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        logger.info("进入source/findAll方法");
        return Result.success(sourceService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable String id) {
        logger.info("进入source/findById方法");
        return Result.success(sourceService.getById(id));
    }

    //根据存储过程名称查询
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        logger.info("进入source/findByName方法,查询名称为:"+name);
        List<Source> sourceList = sourceService.findByName(name);
        StringBuilder str = new StringBuilder();
        String pro_user = sourceList.get(0).getOwner();
        String pro_name = sourceList.get(0).getName();
        for (int i = 0; i < sourceList.size(); i++) {
            str.append(sourceList.get(i).getText());
        }
        Map<String,Object> map = new HashMap<>();
        String strText = str.toString();
        String[] arrStr ;
        arrStr = strText.split(";");
        ArrayList arr = new ArrayList();
        for (int i = 0; i < arrStr.length; i++) {
            if(arrStr[i].contains("INSERT")){
                SourceVO sourceVO = new SourceVO();
                sourceVO.setPro_user(pro_user);
                sourceVO.setPro_name(pro_name);

                String a = arrStr[i].substring(arrStr[i].indexOf("INSERT INTO") + 12);
                String b=a.substring(0,a.indexOf(" "));
                String tmp="";
                if(b.contains("(")){
                    tmp = b.substring(0,b.indexOf("("));
                }else{
                    tmp = b;
                }
                sourceVO.setTmp_user(tmp.substring(0,tmp.indexOf(".")));
                sourceVO.setTmp_name(tmp.substring(tmp.indexOf(".")+1));

                String par="";
                if(arrStr[i].contains("JOIN")){
                    String c = arrStr[i].substring(arrStr[i].indexOf("JOIN") + 5);
                    String d=c.substring(0,c.indexOf(" "));
                    par=d;
                    sourceVO.setPar_user(par.substring(0,par.indexOf(".")));
                    sourceVO.setPar_name(par.substring(par.indexOf(".")+1));
                }

                String con="";
                if(arrStr[i].contains("FROM")){
                    logger.info("con原:"+arrStr[i]);
                    String e = arrStr[i].substring(arrStr[i].indexOf("FROM"));
                    logger.info("e:"+e);

                    /*if(arrStr[i].contains("WHERE")){
                        sourceVO.setCondition(e.substring(0,e.indexOf("WHERE")));
                    }else{
                        sourceVO.setCondition(e);
                    }*/
                    sourceVO.setCondition(e);
                }
                arr.add(sourceVO);
            }
        }
        map.put("records",arr);
        map.put("total",arr.size());
        return Result.success(map);
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String username,
                            @RequestParam(defaultValue = "") String email,
                            @RequestParam(defaultValue = "") String nickname,
                            @RequestParam(defaultValue = "") String address) {
        logger.info("进入source/findPage方法");
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
        queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
        queryWrapper.like("email", email);
        }
        if (!"".equals(nickname)) {
        queryWrapper.like("nickname", nickname);
        }
        if (!"".equals(address)) {
        queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(sourceService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody Source source) {
        logger.info("进入source/save方法");
        return Result.success(sourceService.saveOrUpdate(source));
    }

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        logger.info("进入source/delete方法");
        return Result.success(sourceService.removeById(id));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<String> ids) {
        logger.info("进入source/deleteBatch方法");
        return Result.success(sourceService.removeBatchByIds(ids));
    }

}

