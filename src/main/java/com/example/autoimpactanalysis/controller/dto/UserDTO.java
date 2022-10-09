package com.example.autoimpactanalysis.controller.dto;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: UserDto
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: 接受前端登录请求的参数
 * @Version: V1.0
 */
@Data
public class UserDTO {
    @Alias("用户名")
    private String username;

    @Alias("密码")
    private String password;

    @Alias("昵称")
    private String nickname;

    @Alias("头像")
    private String avatarUrl;

    private String token;
}
