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
 * @ClassName: Broomdatascript
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Broomdatascript 实体类
 */
@Getter
@Setter
@TableName("broomdatascript")
@ApiModel(value = "Broomdatascript对象", description = "")
@ToString
public class Broomdatascript implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提数单id")
    @TableId(type = IdType.AUTO)
    private Integer bdcid;

    @ApiModelProperty("提数单编号")
    private String bdcnumber;

    @ApiModelProperty("提数标题")
    private String bdctitle;

    @ApiModelProperty("内容描述")
    private String contentdescription;

    @ApiModelProperty("附件列表")
    private String attachmentliststr;

    @ApiModelProperty("提数脚本")
    private String broomdatascript;

    @ApiModelProperty("插入时间")
    private Date createtime;

}
