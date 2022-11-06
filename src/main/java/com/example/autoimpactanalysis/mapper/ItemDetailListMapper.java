package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.entity.ItemDetailList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.VO.ItemDetailListVO;
import com.example.autoimpactanalysis.entity.VO.ReportStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ItemDetailListMapper
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description: ItemDetailListMapper 接口
 */
@Mapper
@Component
public interface ItemDetailListMapper extends BaseMapper<ItemDetailList> {
    @Select("select * from item_detail_List where report_id = #{reportId} order by item6_order,item5_order,item4_order,item3_order,item2_order,item1_order,item_level ASC")
    List<ItemDetailList> getItemDetailListByReportId(String reportId);

    int insertItem(ItemDetailList itemDetailList);

    int updateItem(ItemDetailList itemDetailList);

    IPage<ItemDetailList> findPage(Page<ItemDetailList> objectPage, @Param("ew") QueryWrapper<ItemDetailList> queryWrapper);
}
