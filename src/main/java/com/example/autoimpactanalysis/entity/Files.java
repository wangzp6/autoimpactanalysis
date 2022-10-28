package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: Files
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Files 实体类
 */
@Getter
@Setter
@ToString
@TableName("sys_files")
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("提数单编号")
    private Integer bdcId;

    @ApiModelProperty("文件md5")
    private String md5;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用链接,1：可用；0：不可用")
    private Boolean enable;


}
