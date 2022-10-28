package com.example.autoimpactanalysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName: Source
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: Source 实体类
 */
@Getter
@Setter
@ToString
@TableName("ALL_SOURCE_BACKUP")
@ApiModel(value = "Source对象", description = "")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    private String owner;

    private String name;

    private String type;

    private BigDecimal line;

    private String text;

    private BigDecimal originConId;


}
