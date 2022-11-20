package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.autoimpactanalysis.entity.Files;
import com.example.autoimpactanalysis.mapper.FilesMapper;
import com.example.autoimpactanalysis.service.IFilesService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: FilesServiceImpl
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Files 服务实现类
 */
@Service
@DS("mysql")
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

}
