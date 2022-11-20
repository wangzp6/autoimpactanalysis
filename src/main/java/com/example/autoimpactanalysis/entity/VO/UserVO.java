package com.example.autoimpactanalysis.entity.VO;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: UserVO
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: 接受前端登录请求的参数
 * @Version: V1.0
 */
@Data
public class UserVO {
    @Alias("用户名")
    private String userName;

    @Alias("密码")
    private String password;

    @Alias("昵称")
    private String nickName;

    @Alias("头像")
    private String avatarUrl;

    private String token;

}
