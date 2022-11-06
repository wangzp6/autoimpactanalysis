package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: ReportDetail
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: ReportDetail 实体类
 */
@Getter
@Setter
@TableName("report_detail")
@ApiModel(value = "ReportDetail对象", description = "")
public class ReportDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("报表ID")
    @TableId(value = "report_id", type = IdType.AUTO)
    private Integer reportId;

    @ApiModelProperty("报表编号")
    private String reportCode;

    @ApiModelProperty("报表名称")
    private String reportName;

    @ApiModelProperty("报表状态;0:未上线;1:已上线;2:已下线;")
    private String reportState;

    @ApiModelProperty("报表操作标志;A:新增;U:修改")
    private String operationFlag;

    @ApiModelProperty("项目编号")
    private String projectId;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;


}
