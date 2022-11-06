package com.example.autoimpactanalysis.service;

import com.example.autoimpactanalysis.entity.DocumentCode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName: IDocumentCodeService
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentCode 服务接口
 */
public interface IDocumentCodeService extends IService<DocumentCode> {

    List<DocumentCode>  getByDocCode(String docCode);
}
