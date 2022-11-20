package com.example.autoimpactanalysis.entity.VO;

import cn.hutool.core.annotation.Alias;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.example.autoimpactanalysis.entity.Files;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: BroomDataScriptVO
 * @Author: kevin
 * @Date: 2022/7/1 15:25
 * @Description: TODO
 * @Version: V1.0
 */
@Data
public class BroomDataScriptVO extends BroomDataScript {
    @Alias("提数单编号")
    private String bdcId;

    @Alias("提数标题")
    private String bdcTitle;

    @Alias("内容描述")
    private String contentDescription;

    @Alias("附件列表")
    private ArrayList<Files> attachmentList;

    @Alias("提数脚本")
    private String broomDataScript;

    @Alias("负责人")
    private String principal;

    @Alias("操作人")
    private String operator;

    @Alias("是否已删除；0:否；1:已删除")
    private String isDelete;

    @Alias("插入时间")
    private Date insertTime;

    @Alias("修改时间")
    private Date updateTime;
}
