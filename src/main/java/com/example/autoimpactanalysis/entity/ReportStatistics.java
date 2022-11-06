package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName: ReportStatistics
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatistics 实体类
 */
@Getter
@Setter
@ToString
@TableName("report_statistics")
@ApiModel(value = "ReportStatistics对象", description = "")
public class ReportStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("报表id")
    private String reportId;

    @ApiModelProperty("报表名称")
    private String reportName;

    @ApiModelProperty("报表明细")
    private String reportDetail;

    @ApiModelProperty("报表状态")
    private String reportState;

    @ApiModelProperty("文档类型")
    private String docType;

    @ApiModelProperty("文档状态")
    private String docState;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("项目编号")
    private String projectId;

    @ApiModelProperty("操作标志;I:新增;U:修改")
    private String operationFlag;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
