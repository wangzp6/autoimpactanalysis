package com.example.autoimpactanalysis.entity.VO;

import com.example.autoimpactanalysis.entity.ItemDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: ItemDetailVO
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description:
 */
@Data
@ApiModel(value = "ItemDetail对象", description = "")
public class ItemDetailVO extends ItemDetail {

    @ApiModelProperty("元素编号")
    private String itemId;

    @ApiModelProperty("元素标题")
    private String itemTitle;

    @ApiModelProperty("元素名称")
    private String itemName;

    @ApiModelProperty("报表编号")
    private String reportId;

    @ApiModelProperty("元素序号")
    private String itemOrder;

    @ApiModelProperty("元素明细")
    private String itemDetail;

    @ApiModelProperty("父元素编号")
    private String itemParentId;

    @ApiModelProperty(value = "子菜单")
    private List<ItemDetailVO> childrenItemList;


}
