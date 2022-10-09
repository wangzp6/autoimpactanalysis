package com.example.autoimpactanalysis.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: SourceDTO
 * @Author: kevin
 * @Date: 2022-06-16
 * @Version: V1.0
 * @Description: SourceDTO 实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SourceDTO {
    private String pro_user;
    private String pro_name;
    private String tmp_user;
    private String tmp_name;
    private String par_user;
    private String par_name;
    private String condition;
}
