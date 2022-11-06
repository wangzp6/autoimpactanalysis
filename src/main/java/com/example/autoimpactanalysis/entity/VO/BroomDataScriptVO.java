package com.example.autoimpactanalysis.entity.VO;

import cn.hutool.core.annotation.Alias;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.example.autoimpactanalysis.entity.Files;
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
    private Integer bdcId;

    @Alias("提数标题")
    private String bdcTitle;

    @Alias("内容描述")
    private String contentDescription;

    @Alias("附件列表")
    private ArrayList<Files> attachmentList;

    @Alias("提数脚本")
    private String broomDataScript;

    @Alias("插入时间")
    private Date createTime;
}
