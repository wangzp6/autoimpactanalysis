package com.example.autoimpactanalysis.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: User
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: User 实体类
 */

@Data
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;

    @ApiModelProperty("用户名")
    @Alias("用户名")
    private String userName;

    @ApiModelProperty("密码")
    @Alias("密码")
    private String password;

    @ApiModelProperty("昵称")
    @Alias("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    @Alias("邮箱")
    private String email;

    @ApiModelProperty("电话")
    @Alias("电话")
    private String phone;

    @ApiModelProperty("地址")
    @Alias("地址")
    private String address;

    @ApiModelProperty("头像")
    @Alias("头像")
    private String avatarUrl;
	
	@ApiModelProperty("插入时间")
	@Alias("插入时间")
    private Date insertTime;

}
