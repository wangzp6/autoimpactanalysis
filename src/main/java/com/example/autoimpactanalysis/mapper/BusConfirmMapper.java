package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.BusConfirm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.VO.BusConfirmVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: BusConfirmMapper
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: BusConfirmMapper 接口
 */
@Mapper
public interface BusConfirmMapper extends BaseMapper<BusConfirm> {
    IPage<BusConfirmVO> findBusConfirm(IPage<BusConfirmVO> page, @Param("ew") QueryWrapper<BusConfirmVO> queryWrapper);
}
