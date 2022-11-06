package com.example.autoimpactanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.autoimpactanalysis.entity.DocumentCode;
import com.example.autoimpactanalysis.mapper.DocumentCodeMapper;
import com.example.autoimpactanalysis.service.IDocumentCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: DocumentCodeServiceImpl
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentCode 服务实现类
 */
@Service
public class DocumentCodeServiceImpl extends ServiceImpl<DocumentCodeMapper, DocumentCode> implements IDocumentCodeService {

    @Override
    public List<DocumentCode> getByDocCode(String docCode) {
        QueryWrapper<DocumentCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(docCode), "doc_code", docCode);
        List<DocumentCode> list = list(queryWrapper);
        return list;
    }
}
