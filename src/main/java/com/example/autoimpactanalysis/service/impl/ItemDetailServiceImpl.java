package com.example.autoimpactanalysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.autoimpactanalysis.entity.ItemDetail;
import com.example.autoimpactanalysis.entity.VO.ItemDetailVO;
import com.example.autoimpactanalysis.mapper.ItemDetailMapper;
import com.example.autoimpactanalysis.service.IItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ItemDetailServiceImpl
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description: ItemDetail 服务实现类
 */
@Service
@DS("mysql")
public class ItemDetailServiceImpl extends ServiceImpl<ItemDetailMapper, ItemDetail> implements IItemDetailService {

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    @Override
    public List<ItemDetailVO> getItemDetailListByReportId(ItemDetailVO itemDetailVO) {
//        List<ItemDetailVO> sonList = itemDetailMapper.getItemDetailByReportId(itemDetailVO);
//        sonList.forEach(v -> {
//            getItemDetailByParentId(v);
//        });
//        return sonList;

        List<ItemDetailVO> itemDetailVOListP = itemDetailMapper.getItemDetailByReportId(itemDetailVO);
        for (ItemDetailVO itemTemp :itemDetailVOListP) {
            List<ItemDetailVO> itemDetailVOListC = getItemDetailByReportId(itemTemp);
            itemTemp.setChildrenItemList(itemDetailVOListC);
        }
        return itemDetailVOListP;
    }

    public void getItemDetailByParentId(ItemDetailVO itemDetailVO) {
        ItemDetailVO queryCondition = new ItemDetailVO();
        queryCondition.setReportId(itemDetailVO.getReportId());
        queryCondition.setItemParentId(itemDetailVO.getItemId());
        List<ItemDetailVO> sonList = itemDetailMapper.getItemDetailByReportId(queryCondition);
        if (!sonList.isEmpty()) {
            itemDetailVO.setChildrenItemList(sonList);
            sonList.forEach(v -> {
                getItemDetailByParentId(v);
            });
        }
    }

    public List<ItemDetailVO> getItemDetailByReportId(ItemDetailVO itemDetailVO) {
        ItemDetailVO itemDetailVOC = new ItemDetailVO();
        itemDetailVOC.setReportId(itemDetailVO.getReportId());
        itemDetailVOC.setItemParentId(itemDetailVO.getItemId());
        List<ItemDetailVO> itemDetailVOListC = itemDetailMapper.getItemDetailByReportId(itemDetailVOC);
        if (!itemDetailVOListC.isEmpty()) {
            itemDetailVO.setChildrenItemList(itemDetailVOListC);
            for (ItemDetailVO itemTemp :itemDetailVOListC) {
                List<ItemDetailVO> itemDetailVOListCS = getItemDetailByReportId(itemTemp);
                itemTemp.setChildrenItemList(itemDetailVOListCS);
            }
        }
        return itemDetailVOListC;
    }
}
