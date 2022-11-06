package com.example.autoimpactanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ItemDetailList;
import com.example.autoimpactanalysis.entity.VO.ItemDetailListVO;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import com.example.autoimpactanalysis.mapper.ItemDetailListMapper;
import com.example.autoimpactanalysis.service.IItemDetailListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ItemDetailListServiceImpl
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description: ItemDetailList 服务实现类
 */
@Service
public class ItemDetailListServiceImpl extends ServiceImpl<ItemDetailListMapper, ItemDetailList> implements IItemDetailListService {

    @Autowired
    ItemDetailListMapper itemDetailListMapper;

    public List<ItemDetailListVO> getItemDetailListByReportId(String reportId) {
        List<ItemDetailListVO> itemDetailListVOS = new ArrayList<>();
        List<ItemDetailList> itemDetailLists = itemDetailListMapper.getItemDetailListByReportId(reportId);
        if (!itemDetailLists.isEmpty()) {
            for (ItemDetailList item : itemDetailLists) {
                ItemDetailListVO itemDetailListVO = itemList2VO(item);
                itemDetailListVOS.add(itemDetailListVO);
            }
        }
        return itemDetailListVOS;
    }

    public boolean saveOrUpdateItem(ItemDetailListVO itemDetailListVO) {
        ItemDetailList itemDetailList = itemDetailListMapper.selectById(itemDetailListVO.getId());
        int flag;
        if (itemDetailList != null) {
            flag = updateItem(itemDetailListVO);
        } else {
            flag = insertItem(itemDetailListVO);
        }
        if (flag == 0) {
            return false;
        }
        return true;
    }

    @Override
    public IPage<ItemDetailListVO> findPage(Integer pageNum, Integer pageSize, QueryWrapper<ItemDetailList> queryWrapper) {
        IPage<ItemDetailList> itemDetailListIPage = itemDetailListMapper.findPage(new Page<>(pageNum, pageSize), queryWrapper);
        return itemDetailListIPage.convert(u -> {
            ItemDetailListVO v = itemList2VO(u);
//            v.setReportName(u.getReportName());
            BeanUtils.copyProperties(u, v);
            return v;
        });
    }

    public int insertItem(ItemDetailListVO itemDetailListVO) {
        ItemDetailList itemDetailList = itemVO2List(itemDetailListVO);
        int flag = itemDetailListMapper.insertItem(itemDetailList);
        return flag;
    }

    public int updateItem(ItemDetailListVO itemDetailListVO) {
        ItemDetailList itemDetailList = itemVO2List(itemDetailListVO);
        int flag = itemDetailListMapper.updateItem(itemDetailList);
        return flag;
    }

    public ItemDetailList itemVO2List(ItemDetailListVO itemDetailListVO) {
        ItemDetailList itemDetailList = new ItemDetailList();
        itemDetailList.setId(itemDetailListVO.getId());
        itemDetailList.setReportId(itemDetailListVO.getReportId());
        itemDetailList.setItemLevel(itemDetailListVO.getItemLevel());
        itemDetailList.setInsertTime(itemDetailListVO.getInsertTime());
        if (null != itemDetailListVO.getItemLevel() && (1 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem1Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem1Name(itemDetailListVO.getItemName());
            itemDetailList.setItem1Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem1Order(itemDetailListVO.getItemOrder());
        } else if (null != itemDetailListVO.getItemLevel() && (2 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem2Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem2Name(itemDetailListVO.getItemName());
            itemDetailList.setItem2Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem2Order(itemDetailListVO.getItemOrder());
        } else if (null != itemDetailListVO.getItemLevel() && (3 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem3Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem3Name(itemDetailListVO.getItemName());
            itemDetailList.setItem3Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem3Order(itemDetailListVO.getItemOrder());
        } else if (null != itemDetailListVO.getItemLevel() && (4 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem4Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem4Name(itemDetailListVO.getItemName());
            itemDetailList.setItem4Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem4Order(itemDetailListVO.getItemOrder());
        } else if (null != itemDetailListVO.getItemLevel() && (5 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem5Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem5Name(itemDetailListVO.getItemName());
            itemDetailList.setItem5Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem5Order(itemDetailListVO.getItemOrder());
        } else if (null != itemDetailListVO.getItemLevel() && (6 == itemDetailListVO.getItemLevel())) {
            itemDetailList.setItem6Title(itemDetailListVO.getItemTitle());
            itemDetailList.setItem6Name(itemDetailListVO.getItemName());
            itemDetailList.setItem6Detail(itemDetailListVO.getItemDetail());
            itemDetailList.setItem6Order(itemDetailListVO.getItemOrder());
        }
        return itemDetailList;
    }

    public ItemDetailListVO itemList2VO(ItemDetailList itemDetailList) {
        ItemDetailListVO itemDetailListVO = new ItemDetailListVO();
        itemDetailListVO.setId(itemDetailList.getId());
        itemDetailListVO.setReportId(itemDetailList.getReportId());
        itemDetailListVO.setReportName(itemDetailList.getReportName());
        itemDetailListVO.setItemLevel(itemDetailList.getItemLevel());
        itemDetailListVO.setInsertTime(itemDetailList.getInsertTime());
        if (null != itemDetailList.getItemLevel() && (1 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem1Title());
            itemDetailListVO.setItemName(itemDetailList.getItem1Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem1Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem1Order());
        } else if (null != itemDetailList.getItemLevel() && (2 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem2Title());
            itemDetailListVO.setItemName(itemDetailList.getItem2Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem2Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem2Order());
        } else if (null != itemDetailList.getItemLevel() && (3 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem3Title());
            itemDetailListVO.setItemName(itemDetailList.getItem3Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem3Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem3Order());
        } else if (null != itemDetailList.getItemLevel() && (4 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem4Title());
            itemDetailListVO.setItemName(itemDetailList.getItem4Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem4Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem4Order());
        } else if (null != itemDetailList.getItemLevel() && (5 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem5Title());
            itemDetailListVO.setItemName(itemDetailList.getItem5Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem5Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem5Order());
        } else if (null != itemDetailList.getItemLevel() && (6 == itemDetailList.getItemLevel())) {
            itemDetailListVO.setItemTitle(itemDetailList.getItem6Title());
            itemDetailListVO.setItemName(itemDetailList.getItem6Name());
            itemDetailListVO.setItemDetail(itemDetailList.getItem6Detail());
            itemDetailListVO.setItemOrder(itemDetailList.getItem6Order());
        }
        return itemDetailListVO;
    }

}
