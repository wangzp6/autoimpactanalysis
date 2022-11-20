package com.example.autoimpactanalysis.entity.VO;

import lombok.Data;

/**
 * @ClassName: SourceVO
 * @Author: kevin
 * @Date: 2022-06-16
 * @Version: V1.0
 * @Description:
 */

@Data
public class SourceVO {
    private String pro_user;
    private String pro_name;
    private String tmp_user;
    private String tmp_name;
    private String par_user;
    private String par_name;
    private String condition;
}
