package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.controller.dto.BroomdatascriptDTO;
import com.example.autoimpactanalysis.entity.Broomdatascript;
import com.example.autoimpactanalysis.entity.Files;
import com.example.autoimpactanalysis.mapper.BroomdatascriptMapper;
import com.example.autoimpactanalysis.mapper.FilesMapper;
import com.example.autoimpactanalysis.service.IBroomdatascriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * @ClassName: BroomdatascriptServiceImpl
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Broomdatascript 服务实现类
 */
@Service
@DS("mysql")
public class BroomdatascriptServiceImpl extends ServiceImpl<BroomdatascriptMapper, Broomdatascript> implements IBroomdatascriptService {

    @Autowired
    private BroomdatascriptMapper broomdatascriptMapper;

    @Autowired
    private FilesMapper filesMapper;

    @Override
    public IPage<Broomdatascript> findBroomdatascript(Integer pageNum, Integer pageSize, QueryWrapper<Broomdatascript> queryWrapper) {
        IPage<Broomdatascript> broomdatascriptIPage= broomdatascriptMapper.findBroomdatascript(new Page<>(pageNum, pageSize), queryWrapper);
//        return broomdatascriptIPage;
        return broomdatascriptIPage.convert( u ->{
            BroomdatascriptDTO v = new BroomdatascriptDTO();
            Broomdatascript broomdatascript = broomdatascriptMapper.selectById(u.getBdcid());
            if (broomdatascript!=null){
                ArrayList<Files> filesList = filesMapper.findByBdcid(u.getBdcid());
                v.setAttachmentlist(filesList);//设置属性
            }
            BeanUtils.copyProperties(u, v);//拷贝
            return v;
        });
    }

    @Override
    public Integer removeByIdWithFile(Integer bdcid) {
        ArrayList<Files> filesList = filesMapper.findByBdcid(bdcid);
        for (int i = 0; i < filesList.size(); i++) {
            Files files =filesList.get(i);
            files.setIsDelete(true);
            filesMapper.updateById(files);
        }
        return broomdatascriptMapper.deleteById(bdcid);
    }
}
