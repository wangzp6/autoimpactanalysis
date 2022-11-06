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
 * @ClassName: ProjectDetail
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ProjectDetail 实体类
 */
@Getter
@Setter
@ToString
@TableName("project_detail")
@ApiModel(value = "ProjectDetail对象", description = "")
public class ProjectDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    @ApiModelProperty("项目ID")
    private String projectId;

    @ApiModelProperty("项目编号")
    private String projectCode;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("上线时间")
    private Date putIntoProTime;
    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;
    @ApiModelProperty("插入时间")
    private Date insertTime;


}
