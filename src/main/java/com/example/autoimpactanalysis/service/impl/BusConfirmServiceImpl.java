package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.BusConfirm;
import com.example.autoimpactanalysis.entity.VO.BusConfirmVO;
import com.example.autoimpactanalysis.mapper.BusConfirmMapper;
import com.example.autoimpactanalysis.service.IBusConfirmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: BusConfirmServiceImpl
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: BusConfirm 服务实现类
 */
@Service
@DS("mysql")
public class BusConfirmServiceImpl extends ServiceImpl<BusConfirmMapper, BusConfirm> implements IBusConfirmService {

    @Autowired
    private BusConfirmMapper busConfirmMapper;

    @Override
    public IPage<BusConfirmVO> findBusConfirm(Integer pageNum, Integer pageSize, QueryWrapper<BusConfirmVO> queryWrapper) {
        IPage<BusConfirmVO> reportStatisticsIPage = busConfirmMapper.findBusConfirm(new Page<>(pageNum, pageSize), queryWrapper);

        return reportStatisticsIPage;
    }

    @Override
    public List<BusConfirm> findList() {
        QueryWrapper<BusConfirm> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("is_delete", "1");
        List<BusConfirm> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<BusConfirm> getBybBusConfirmId(String busConfirmId) {
        QueryWrapper<BusConfirm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bus_confirm_id", busConfirmId);
        queryWrapper.ne("is_delete", "1");
        List<BusConfirm> list = list(queryWrapper);
        return list;
    }

    @Override
    public int removeByBusConfirmId(String busConfirmId,String operator) {
        BusConfirm busConfirm = new BusConfirm();
        UpdateWrapper<BusConfirm> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("bus_confirm_id", busConfirmId);
        updateWrapper.ne("is_delete", "1");
        updateWrapper.set("operator", operator);
        updateWrapper.set("update_time", new Date());
        updateWrapper.set("is_delete", "1");
        return busConfirmMapper.update(busConfirm,updateWrapper);
    }

    @Override
    public int removeBatchByBusConfirmIds(List<BusConfirm> busConfirms) {
        return busConfirmMapper.removeBatchByBusConfirmIds(busConfirms);
    }
}
