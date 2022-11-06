package com.example.autoimpactanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import com.example.autoimpactanalysis.mapper.DocumentDetailMapper;
import com.example.autoimpactanalysis.mapper.ReportDetailMapper;
import com.example.autoimpactanalysis.service.IDocumentDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: DocumentDetailServiceImpl
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 服务实现类
 */
@Service
public class DocumentDetailServiceImpl extends ServiceImpl<DocumentDetailMapper, DocumentDetail> implements IDocumentDetailService {
    @Autowired
    private DocumentDetailMapper documentDetailMapper;

    @Override
    public List<DocumentDetail> findList() {
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("is_delete", "1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public IPage<DocumentDetailVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<DocumentDetailVO> queryWrapper) {
        IPage<DocumentDetailVO> ReportDetailIPage = documentDetailMapper.findPage(new Page<>(pageNum, pageSize), queryWrapper);
        return ReportDetailIPage;
    }

    @Override
    public List<DocumentDetail> getBySameDoc(DocumentDetail documentDetail) {
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(documentDetail.getReportId()), "report_id", documentDetail.getReportId());
        queryWrapper.like(StringUtils.hasText(documentDetail.getDocState()), "doc_state", documentDetail.getDocState());
        queryWrapper.like(StringUtils.hasText(documentDetail.getDocCode()), "doc_code", documentDetail.getDocCode());
        queryWrapper.ne("is_delete","1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<DocumentDetail> getByDocId(Integer codeId) {
        QueryWrapper<DocumentDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code_id", codeId);
        queryWrapper.ne("is_delete", "1");
        List<DocumentDetail> list = list(queryWrapper);
        return list;
    }

    @Override
    public int removeByDocId(Integer docId) {
        DocumentDetail documentDetail = new DocumentDetail();
        UpdateWrapper<DocumentDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("doc_id", docId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("is_delete", "1");
        return documentDetailMapper.update(documentDetail,updateWrapper);
    }

    @Override
    public int removeBatchByDocIds(List<Integer> docIds) {
        DocumentDetail documentDetail = new DocumentDetail();
        try {
            UpdateWrapper<DocumentDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ne("is_delete", "1");
            updateWrapper.set("is_delete", "1").in("doc_id",docIds);
            return documentDetailMapper.update(documentDetail,updateWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
