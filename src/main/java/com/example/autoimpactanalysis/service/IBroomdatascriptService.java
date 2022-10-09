package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.autoimpactanalysis.entity.Broomdatascript;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @ClassName: IBroomdatascriptService
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Broomdatascript 服务接口
 */
public interface IBroomdatascriptService extends IService<Broomdatascript> {
    IPage<Broomdatascript> findBroomdatascript(Integer pageNum, Integer pageSize, QueryWrapper<Broomdatascript> queryWrapper);

    Integer removeByIdWithFile(Integer id);
}
