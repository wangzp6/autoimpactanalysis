package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.BusConfirm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.VO.BusConfirmVO;

import java.util.List;

/**
 * @ClassName: IBusConfirmService
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: BusConfirm 服务接口
 */
public interface IBusConfirmService extends IService<BusConfirm> {

    List<BusConfirm> findList();

    List<BusConfirm> getBybBusConfirmId(String busConfirmId);

    IPage<BusConfirmVO> findBusConfirm(Integer pageNum, Integer pageSize, QueryWrapper<BusConfirmVO> queryWrapper);

    int removeByBusConfirmId(String busConfirmId,String operator);

    int removeBatchByBusConfirmIds(List<BusConfirm> busConfirms);



}
