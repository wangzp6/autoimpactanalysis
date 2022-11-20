package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import com.example.autoimpactanalysis.mapper.DocumentDetailMapper;
import com.example.autoimpactanalysis.service.IDocumentDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: DocumentDetailServiceImpl
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 服务实现类
 */
@Slf4j
@Service
@DS("mysql")
public class DocumentDetailServiceImpl extends ServiceImpl<DocumentDetailMapper, DocumentDetail> implements IDocumentDetailService {
    @Autowired
    private DocumentDetailMapper documentDetailMapper;

    @Override
    public List<DocumentDetail> findList() {
        log.info("进入DocumentDetailServiceImpl/findList方法");
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("is_delete", "1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public IPage<DocumentDetailVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<DocumentDetailVO> queryWrapper) {
        log.info("进入DocumentDetailServiceImpl/findPage方法");
        IPage<DocumentDetailVO> ReportDetailIPage = documentDetailMapper.findPage(new Page<>(pageNum, pageSize), queryWrapper);
        return ReportDetailIPage;
    }

    @Override
    public List<DocumentDetail> getBySameDocVO(DocumentDetailVO documentDetailVO) {
        log.info("进入DocumentDetailServiceImpl/getBySameDocVO方法");
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getReportId()), "report_id", documentDetailVO.getReportId());
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getDocState()), "doc_state", documentDetailVO.getDocState());
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getDocCode()), "doc_code", documentDetailVO.getDocCode().trim());
        queryWrapper.ne("is_delete", "1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<DocumentDetailVO> getBySameChapter(DocumentDetailVO documentDetailVO){
        log.info("进入DocumentDetailServiceImpl/getBySameChapter方法");
        QueryWrapper<DocumentDetailVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getProjectId()), "p.project_id", documentDetailVO.getProjectId());
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getReportName()), "r.report_name", documentDetailVO.getReportName().trim());
        queryWrapper.eq(StringUtils.hasText(documentDetailVO.getDocCode()), "d.doc_code", documentDetailVO.getDocCode().trim());
        queryWrapper.eq("d.doc_state", "0");
        queryWrapper.eq("r.report_type", "1");
        queryWrapper.ne("d.is_delete","1");
        return documentDetailMapper.getBySameChapter(documentDetailVO, queryWrapper);
    }

    @Override
    public List<DocumentDetail> getByDocId(String codeId) {
        log.info("进入DocumentDetailServiceImpl/getByDocId方法");
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_id", codeId);
        queryWrapper.ne("is_delete", "1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public DocumentDetailVO getDocVOByDocId(String codeId) {
        log.info("进入DocumentDetailServiceImpl/getDocVOByDocId方法");
        return documentDetailMapper.getDocVOByDocId(codeId);
    }

    @Override
    public int removeByDocId(String docId, String operator) {
        log.info("进入DocumentDetailServiceImpl/removeByDocId方法");
        DocumentDetail documentDetail = new DocumentDetail();
        UpdateWrapper<DocumentDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("doc_id", docId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("operator", operator);
        updateWrapper.set("update_time", new Date());
        updateWrapper.set("is_delete", "1");
        return documentDetailMapper.update(documentDetail, updateWrapper);
    }

    @Override
    public int removeBatchByDocIds(List<DocumentDetail> docs) {
        log.info("进入DocumentDetailServiceImpl/removeBatchByDocIds方法");
        return documentDetailMapper.removeBatchByDocIds(docs);
    }

}
