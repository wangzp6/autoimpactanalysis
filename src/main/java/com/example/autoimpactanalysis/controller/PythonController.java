package com.example.autoimpactanalysis.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.autoimpactanalysis.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: PythonController
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: PythonController 前端控制器
 */
@RestController
@RequestMapping("/topython")
public class PythonController {

    private static Logger log = LoggerFactory.getLogger(PythonController.class);

    //导入发行人客户画像
    @PostMapping("/importfxrParam")
    public Result importfxrParam(MultipartFile file) throws Exception {
        log.info("进入topython/importfxrParam");
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> list = reader.readAll();
        String str = getValues(list);

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python D:\\JavaCode\\springbootbai\\script\\Issuer_UserPersona.py " + str);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line:"+line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
    //导入承销商客户画像
    @PostMapping("/importcxsParam")
    public Result importcxsParam(MultipartFile file) throws Exception {
        log.info("进入topython/importcxsParam");
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> list = reader.readAll();
        String str = getValues(list);

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python D:\\JavaCode\\springbootbai\\script\\UnderWriter_UserPersona.py " + str);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line:"+line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
    //导入承销行为参数分析
    @PostMapping("/importcxxwParam")
    public Result importcxxwParam(MultipartFile file) throws Exception {
        log.info("进入topython/importcxxwParam");
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> list = reader.readAll();
        String str = getValues(list);

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python D:\\JavaCode\\springbootbai\\script\\BehaviorAnalysis.py " + str);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line:"+line);
            }
            BufferedReader inerr = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"utf-8"));
            String line1 = null;
            while ((line1 = inerr.readLine()) != null) {
                log.info("line1:"+line1);
            }
            in.close();
            inerr.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    //导入承销行为预测
    @PostMapping("/importcxycParam")
    public Result importcxycParam(MultipartFile file) throws Exception {
        log.info("进入topython/importcxycParam");
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> list = reader.readAll();
        String str = getValues(list);

        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python D:\\JavaCode\\springbootbai\\script\\Underwrite_Forcast.py " + str);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line:"+line);
            }
            BufferedReader inerr = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"utf-8"));
            String line1 = null;
            while ((line1 = inerr.readLine()) != null) {
                log.info("line1:"+line1);
            }
            in.close();
            inerr.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }


    public String getValues(List<Map<String, Object>> list) {
        log.info(list.toString());
        StringBuilder sb = new StringBuilder();
        for (Map<String,Object> a : list) {
            String paramType = a.get("paramType").toString();
            if ("date".equals(paramType)) {
                Date date = (Date) a.get("paramValue");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                sb.append(a.get("paramName").toString().trim()+":"+dateString+",");
            } else if ("int".equals(paramType)) {
                Long numLong = (Long) a.get("paramValue");
                sb.append(a.get("paramName").toString().trim()+":"+numLong.toString()+",");
            } else if ("long".equals(paramType)) {
                Long num = (Long) a.get("paramValue");
                sb.append(a.get("paramName").toString().trim()+":"+num.toString()+",");
            } else if ("double".equals(paramType)) {
                Double numDouble = (Double) a.get("paramValue");
                sb.append(a.get("paramName").toString().trim()+":"+numDouble.toString()+",");
            }else if ("string".equals(paramType)) {
                sb.append(a.get("paramName").toString().trim()+":"+a.get("paramValue").toString().trim()+",");
            }
        }
        String str = sb.substring(0,sb.length()-1);
        log.info("入参:"+str);
        return str;
    }

}

