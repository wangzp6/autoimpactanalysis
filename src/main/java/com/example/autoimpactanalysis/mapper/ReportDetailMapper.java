package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.ReportDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.VO.ReportDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: ReportDetailMapper
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: ReportDetailMapper 接口
 */
@Mapper
public interface ReportDetailMapper extends BaseMapper<ReportDetail> {

    IPage<ReportDetailVO> findReports(IPage<ReportDetailVO> page, @Param("ew") QueryWrapper<ReportDetailVO> queryWrapper);
}
