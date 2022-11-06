package com.example.autoimpactanalysis.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.autoimpactanalysis.entity.BusConfirm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BusConfirmVO
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description:
 */
@Getter
@Setter
@ApiModel(value = "BusConfirm对象", description = "")
public class BusConfirmVO extends BusConfirm {

    @ApiModelProperty("ID")
    @TableId(value = "bus_confirm_id", type = IdType.AUTO)
    private Integer busConfirmId;

    @ApiModelProperty("业务确认问题")
    private String busConfirmQuestion;

    @ApiModelProperty("业务确认回答")
    private String busConfirmReply;

    @ApiModelProperty("业务确认状态")
    private String busConfirmState;

    @ApiModelProperty("业务确认类型")
    private String busConfirmType;

    @ApiModelProperty("业务确认时间")
    private Date busConfirmTime;

    @ApiModelProperty("提问人")
    private String questioner;

    @ApiModelProperty("回复人")
    private String replier;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("报表ID")
    private String reportId;

    @ApiModelProperty("报表编号")
    private String reportCode;

    @ApiModelProperty("报表名称")
    private String reportName;

    @ApiModelProperty("项目ID")
    private String projectId;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
