package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;

import java.util.List;

/**
 * @ClassName: IDocumentDetailService
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 服务接口
 */
public interface IDocumentDetailService extends IService<DocumentDetail> {

    IPage<DocumentDetailVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<DocumentDetailVO> queryWrapper);

    List<DocumentDetail> getBySameDoc(DocumentDetail documentDetail);

    List<DocumentDetail> findList();

    List<DocumentDetail> getByDocId(Integer docId);

    int removeByDocId(Integer docId);

    int removeBatchByDocIds(List<Integer> docIds);
}
