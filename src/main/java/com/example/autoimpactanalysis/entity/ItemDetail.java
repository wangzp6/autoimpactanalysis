package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: ItemDetail
 * @Author: kevin
 * @Date: 2022-10-19
 * @Version: V1.0
 * @Description: ItemDetail 实体类
 */
@Data
@TableName("item_detail")
@ApiModel(value = "ItemDetail对象", description = "")
public class ItemDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("元素编号")
    @TableId(value = "item_id", type = IdType.AUTO)
    private String itemId;

    @ApiModelProperty("元素名称")
    private String itemName;

    @ApiModelProperty("报表编号")
    private String reportId;

    @ApiModelProperty("元素明细")
    private String itemDetail;

    @ApiModelProperty("父元素编号")
    private String itemParentId;

    @ApiModelProperty("元素序号")
    private String itemOrder;

}
