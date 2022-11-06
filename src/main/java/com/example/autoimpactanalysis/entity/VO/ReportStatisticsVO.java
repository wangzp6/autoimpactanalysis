package com.example.autoimpactanalysis.entity.VO;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.autoimpactanalysis.entity.ItemDetail;
import com.example.autoimpactanalysis.entity.ReportStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @ClassName: ReportStatisticsVO
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ReportStatisticsVO 实体类
 */
@Getter
@Setter
@ApiModel(value = "ReportStatisticsVO对象", description = "")
public class ReportStatisticsVO extends ReportStatistics {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("报表id")
    private String reportId;

    @ApiModelProperty("报表名称")
    private String reportName;

    @ApiModelProperty("操作标志;I:新增;U:修改")
    private String operationFlag;

    @ApiModelProperty("报表明细")
    private String reportDetail;

    @ApiModelProperty("报表状态")
    private String reportState;

    @ApiModelProperty("文档类型")
    private String docType;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("项目编号")
    private String projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @Alias("项目明细")
    private ArrayList<ItemDetail> itemDetailsList;
}
