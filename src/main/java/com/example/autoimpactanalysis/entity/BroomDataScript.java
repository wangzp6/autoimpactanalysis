package com.example.autoimpactanalysis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName: BroomDataScript
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScript 实体类
 */
@Getter
@Setter
@ToString
@TableName("broom_data_script")
@ApiModel(value = "BroomDataScript对象", description = "")
public class BroomDataScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提数单id")
    @TableId(type = IdType.AUTO)
    private Integer bdcId;

    @ApiModelProperty("提数单编号")
    private String bdcNumber;

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

    @ApiModelProperty("插入时间")
    private Date createTime;

}
