package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.ItemDetailList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.example.autoimpactanalysis.entity.VO.ItemDetailListVO;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;

import java.util.List;

/**
 * @ClassName: IItemDetailListService
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description: ItemDetailList 服务接口
 */
public interface IItemDetailListService extends IService<ItemDetailList> {

    List<ItemDetailListVO> getItemDetailListByReportId(String reportId);

    boolean saveOrUpdateItem(ItemDetailListVO itemDetailListVO);

    IPage<ItemDetailListVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<ItemDetailList> queryWrapper);
}
