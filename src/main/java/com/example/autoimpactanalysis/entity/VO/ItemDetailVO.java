package com.example.autoimpactanalysis.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.autoimpactanalysis.entity.ItemDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ItemDetailVO
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description:
 */
@Getter
@Setter
@ApiModel(value = "ItemDetail对象", description = "")
public class ItemDetailVO extends ItemDetail{

    @ApiModelProperty("元素编号")
    private Integer itemId;

    @ApiModelProperty("元素标题")
    private Integer itemTitle;

    @ApiModelProperty("元素名称")
    private String itemName;

    @ApiModelProperty("报表编号")
    private String reportId;

    @ApiModelProperty("元素序号")
    private Integer itemOrder;

    @ApiModelProperty("元素明细")
    private String itemDetail;

    @ApiModelProperty("父元素编号")
    private Integer itemParentId;

    @ApiModelProperty(value = "子菜单")
    private List<ItemDetailVO> childrenItemList;


}
