package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.VO.DocumentDetailVO;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: DocumentDetailMapper
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetailMapper 接口
 */
@Mapper
public interface DocumentDetailMapper extends BaseMapper<DocumentDetail> {

    IPage<DocumentDetailVO> findPage(IPage<ReportDetailVO> page, @Param("ew")QueryWrapper<DocumentDetailVO> queryWrapper);

    int removeBatchByDocIds(List<DocumentDetail> docs);

    List<DocumentDetailVO> getBySameChapter(DocumentDetailVO documentDetailVO, @Param("ew")QueryWrapper<DocumentDetailVO> queryWrapper);

    DocumentDetailVO getDocVOByDocId(String codeId);
}
