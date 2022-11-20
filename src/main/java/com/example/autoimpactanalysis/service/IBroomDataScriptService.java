package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @ClassName: IBroomDataScriptService
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScript 服务接口
 */
public interface IBroomDataScriptService extends IService<BroomDataScript> {
    IPage<BroomDataScript> findBroomDataScript(Integer pageNum, Integer pageSize, QueryWrapper<BroomDataScript> queryWrapper);

    int removeByIdWithFile(String bdcId);
}
