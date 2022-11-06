package com.example.autoimpactanalysis.mapper;

import com.example.autoimpactanalysis.entity.ItemDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.VO.ItemDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ItemDetailMapper
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description: ItemDetailMapper 接口
 */
@Mapper
@Component
public interface ItemDetailMapper extends BaseMapper<ItemDetail> {

    List<ItemDetailVO> getItemDetailByReportId(ItemDetailVO itemDetailVO);
}
