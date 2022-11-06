package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import com.example.autoimpactanalysis.entity.ReportStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ReportStatisticsMapper
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatisticsMapper 接口
 */
@Mapper
@Component
public interface ReportStatisticsMapper extends BaseMapper<ReportStatistics> {

    IPage<ReportStatisticsVO> findReports(IPage<ReportStatisticsVO> page, @Param("ew") QueryWrapper<ReportStatisticsVO> queryWrapper);
}
