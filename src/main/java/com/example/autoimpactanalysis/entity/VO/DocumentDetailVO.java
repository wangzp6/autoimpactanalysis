package com.example.autoimpactanalysis.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.autoimpactanalysis.entity.DocumentDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: DocumentDetail
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 实体类
 */
@Data
@ApiModel(value = "DocumentDetail对象", description = "")
public class DocumentDetailVO extends DocumentDetail {

    @ApiModelProperty("文档ID")
    @TableId(value = "doc_id", type = IdType.AUTO)
    private String docId;

    @ApiModelProperty("文档类型码值")
    private String docCode;

    @ApiModelProperty("文档类型")
    private String docType;

    @ApiModelProperty("文档状态;0:未定稿;1:已定稿;")
    private String docState;

    @ApiModelProperty("文档明细")
    private String docDetail;

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

    @ApiModelProperty("非报表章节;1:是")
    private String reportType;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
