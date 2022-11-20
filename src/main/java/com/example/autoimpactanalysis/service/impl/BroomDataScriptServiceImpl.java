package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.VO.BroomDataScriptVO;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.example.autoimpactanalysis.entity.Files;
import com.example.autoimpactanalysis.mapper.BroomDataScriptMapper;
import com.example.autoimpactanalysis.mapper.FilesMapper;
import com.example.autoimpactanalysis.service.IBroomDataScriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @ClassName: BroomDataScriptServiceImpl
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScript 服务实现类
 */
@Slf4j
@Service
@DS("mysql")
public class BroomDataScriptServiceImpl extends ServiceImpl<BroomDataScriptMapper, BroomDataScript> implements IBroomDataScriptService {

    @Autowired
    private BroomDataScriptMapper broomDataScriptMapper;

    @Autowired
    private FilesMapper filesMapper;

    @Override
    public IPage<BroomDataScript> findBroomDataScript(Integer pageNum, Integer pageSize, QueryWrapper<BroomDataScript> queryWrapper) {
        IPage<BroomDataScript> broomDataScriptIPage= broomDataScriptMapper.findBroomDataScript(new Page<>(pageNum, pageSize), queryWrapper);
        return broomDataScriptIPage.convert( u ->{
            BroomDataScriptVO v = new BroomDataScriptVO();
            BroomDataScript broomdatascript = broomDataScriptMapper.selectById(u.getBdcId());
            if (broomdatascript!=null){
                ArrayList<Files> filesList = filesMapper.findByBdcId(u.getBdcId());
                v.setAttachmentList(filesList);//设置属性
            }
            BeanUtils.copyProperties(u, v);//拷贝
            return v;
        });
    }

    @Override
    public int removeByIdWithFile(String bdcId) {
        ArrayList<Files> filesList = filesMapper.findByBdcId(bdcId);
        for (Files files : filesList) {
            files.setIsDelete("1");
            filesMapper.updateById(files);
        }
        return broomDataScriptMapper.deleteById(bdcId);
    }
}
