package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.common.Constants;
import com.example.autoimpactanalysis.entity.Source;
import com.example.autoimpactanalysis.exception.ServiceException;
import com.example.autoimpactanalysis.mapper.SourceMapper;
import com.example.autoimpactanalysis.service.ISourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: SourceServiceImpl
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Source 服务实现类
 */
@Service
@DS("oracle-edw")
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements ISourceService {

    @Override
    public List<Source> findByName(String name){
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>();
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }
        List<Source> list = list(queryWrapper);
        return list;
    }

}
