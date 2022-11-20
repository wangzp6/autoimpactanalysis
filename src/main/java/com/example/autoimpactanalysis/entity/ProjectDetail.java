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
 * @ClassName: ProjectDetail
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ProjectDetail 实体类
 */
@Data
@TableName("project_detail")
@ApiModel(value = "ProjectDetail对象", description = "")
public class ProjectDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目ID")
    @TableId(value = "project_id", type = IdType.AUTO)
    private String projectId;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("投产时间")
    private Date putIntoProTime;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("是否已删除；0:否；1:已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
