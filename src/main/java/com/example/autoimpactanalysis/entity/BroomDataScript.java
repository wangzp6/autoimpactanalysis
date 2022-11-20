package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: BroomDataScript
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScript 实体类
 */
@Data
@TableName("broom_data_script")
@ApiModel(value = "BroomDataScript对象", description = "")
public class BroomDataScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提数单id")
    @TableId(value = "bdc_id", type = IdType.AUTO)
    private String bdcId;

    @ApiModelProperty("提数单编号")
    private String bdcCode;

    @ApiModelProperty("提数标题")
    private String bdcTitle;

    @ApiModelProperty("内容描述")
    private String contentDescription;

    @ApiModelProperty("附件列表")
    private String attachmentListStr;

    @ApiModelProperty("提数脚本")
    private String broomDataScript;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("是否已删除；0:否；1:已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
