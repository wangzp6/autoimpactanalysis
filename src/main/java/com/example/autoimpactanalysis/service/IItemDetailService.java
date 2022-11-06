package com.example.autoimpactanalysis.service;

import com.example.autoimpactanalysis.entity.ItemDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.VO.ItemDetailVO;

import java.util.List;

/**
 * @ClassName: IItemDetailService
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description: ItemDetail 服务接口
 */
public interface IItemDetailService extends IService<ItemDetail> {

    List<ItemDetailVO> getItemDetailListByReportId(ItemDetailVO itemDetailVO);
}
