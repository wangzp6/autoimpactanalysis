package com.example.autoimpactanalysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.autoimpactanalysis.entity.VO.UserVO;
import com.example.autoimpactanalysis.entity.User;

/**
 * @ClassName: IUserService
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: User 服务接口
 */
public interface IUserService extends IService<User> {

    UserVO login(UserVO userVO);

    User register(UserVO userVO);
}
