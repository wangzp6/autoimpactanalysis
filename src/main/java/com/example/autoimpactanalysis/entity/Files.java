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
 * @ClassName: Files
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Files 实体类
 */
@Data
@TableName("sys_files")
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "file_id", type = IdType.AUTO)
    private String fileId;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件大小，单位：字节（b）")
    private Long fileSize;

    @ApiModelProperty("下载链接")
    private String fileUrl;

    @ApiModelProperty("提数单编号")
    private String bdcId;

    @ApiModelProperty("文件md5")
    private String fileMd5;

    @ApiModelProperty("是否禁用链接,1：可用；0：不可用")
    private String enable;

    @ApiModelProperty("是否删除,0:未删除；1：已删除")
    private String isDelete;

    @ApiModelProperty("插入时间")
    private Date insertTime;


}
