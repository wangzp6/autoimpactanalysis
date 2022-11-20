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
 * @ClassName: DocumentCode
 * @Author: kevin
 * @Date: 2022-11-04
 * @Version: V1.0
 * @Description: DocumentCode 实体类
 */
@Data
@TableName("document_code")
@ApiModel(value = "DocumentCode对象", description = "")
public class DocumentCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("码值ID")
    @TableId(value = "code_id", type = IdType.AUTO)
    private String codeId;

    @ApiModelProperty("文档类型码值")
    private String docCode;

    @ApiModelProperty("文档类型")
    private String docType;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("是否已删除；0:否；1:已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
