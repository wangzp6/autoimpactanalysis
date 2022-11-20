package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.ReportDetail;
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

    List<DocumentDetail> findList();

    List<DocumentDetail> getByDocId(String docId);

    DocumentDetailVO getDocVOByDocId(String codeId);

    IPage<DocumentDetailVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<DocumentDetailVO> queryWrapper);

    List<DocumentDetail> getBySameDocVO(DocumentDetailVO documentDetailVO);

    List<DocumentDetailVO> getBySameChapter(DocumentDetailVO documentDetailVO);

    int removeByDocId(String docId, String operator);

    int removeBatchByDocIds(List<DocumentDetail> docs);
}
