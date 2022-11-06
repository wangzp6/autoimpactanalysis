package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: ItemDetailList
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description: ItemDetailList 实体类
 */
@Getter
@Setter
@TableName("item_detail_list")
@ApiModel(value = "ItemDetailList对象", description = "")
public class ItemDetailList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("报表id")
    private String reportId;

    @TableField(exist = false)
    @ApiModelProperty("报表名称")
    private String reportName;

    @ApiModelProperty("一级元素序号")
    private Integer item1Order;

    @ApiModelProperty("一级元素标题")
    private String item1Title;

    @ApiModelProperty("一级元素名称")
    private String item1Name;

    @ApiModelProperty("一级元素明细")
    private String item1Detail;

    @ApiModelProperty("二级元素序号")
    private Integer item2Order;

    @ApiModelProperty("二级元素标题")
    private String item2Title;

    @ApiModelProperty("二级元素名称")
    private String item2Name;

    @ApiModelProperty("二级元素明细")
    private String item2Detail;

    @ApiModelProperty("三级元素序号")
    private Integer item3Order;

    @ApiModelProperty("三级元素标题")
    private String item3Title;

    @ApiModelProperty("三级元素名称")
    private String item3Name;

    @ApiModelProperty("三级元素明细")
    private String item3Detail;

    @ApiModelProperty("四级元素序号")
    private Integer item4Order;

    @ApiModelProperty("四级元素标题")
    private String item4Title;

    @ApiModelProperty("四级元素名称")
    private String item4Name;

    @ApiModelProperty("四级元素明细")
    private String item4Detail;

    @ApiModelProperty("五级元素序号")
    private Integer item5Order;

    @ApiModelProperty("五级元素标题")
    private String item5Title;

    @ApiModelProperty("五级元素名称")
    private String item5Name;

    @ApiModelProperty("五级元素明细")
    private String item5Detail;

    @ApiModelProperty("六级元素序号")
    private Integer item6Order;

    @ApiModelProperty("六级元素标题")
    private String item6Title;

    @ApiModelProperty("六级元素名称")
    private String item6Name;

    @ApiModelProperty("六级元素明细")
    private String item6Detail;

    @ApiModelProperty("元素级别")
    private Integer itemLevel;

    @ApiModelProperty("插入时间")
    private Date insertTime;


}
