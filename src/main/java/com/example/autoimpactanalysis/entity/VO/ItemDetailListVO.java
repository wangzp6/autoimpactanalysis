package com.example.autoimpactanalysis.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.autoimpactanalysis.entity.ItemDetailList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ItemDetailListVO
 * @Author: kevin
 * @Date: 2022-10-20
 * @Version: V1.0
 * @Description:
 */
@Getter
@Setter
@ApiModel(value = "ItemDetailList对象", description = "")
public class ItemDetailListVO extends ItemDetailList {

      @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("报表id")
      private String reportId;

      @ApiModelProperty("报表名称")
      private String reportName;

      @ApiModelProperty("元素序号")
      private Integer itemOrder;

      @ApiModelProperty("元素标题")
      private String itemTitle;

      @ApiModelProperty("元素名称")
      private String itemName;

      @ApiModelProperty("元素明细")
      private String itemDetail;

      @ApiModelProperty("元素级别")
      private Integer itemLevel;

      @ApiModelProperty("插入时间")
      private Date insertTime;


}
