package com.example.autoimpactanalysis.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.example.autoimpactanalysis.entity.Broomdatascript;
import com.example.autoimpactanalysis.entity.Files;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: BroomdatascriptDTO
 * @Author: kevin
 * @Date: 2022/7/1 15:25
 * @Description: TODO
 * @Version: V1.0
 */
@Data
public class BroomdatascriptDTO extends Broomdatascript {
    @Alias("提数单编号")
    private Integer bdcid;

    @Alias("提数标题")
    private String bdctitle;

    @Alias("内容描述")
    private String contentdescription;

    @Alias("附件列表")
    private ArrayList<Files> attachmentlist;

    @Alias("提数脚本")
    private String broomdatascript;

    @Alias("插入时间")
    private Date createtime;
}
