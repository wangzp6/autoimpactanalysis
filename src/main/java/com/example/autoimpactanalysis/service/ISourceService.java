package com.example.autoimpactanalysis.service;

import com.example.autoimpactanalysis.entity.Source;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName: ISourceService
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Source 服务接口
 */
public interface ISourceService extends IService<Source> {

    List<Source> findByName(String name);
}
