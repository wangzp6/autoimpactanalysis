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
 * @ClassName: DocumentDetail
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentDetail 实体类
 */
@Getter
@Setter
@TableName("document_detail")
@ApiModel(value = "DocumentDetail对象", description = "")
public class DocumentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "doc_id", type = IdType.AUTO)
    private Integer docId;

    @ApiModelProperty("文档类型")
    private String docCode;

    @ApiModelProperty("文档状态;0:未定稿;1:已定稿;")
    private String docState;

    @ApiModelProperty("文档明细")
    private String docDetail;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("报表id")
    private String reportId;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
