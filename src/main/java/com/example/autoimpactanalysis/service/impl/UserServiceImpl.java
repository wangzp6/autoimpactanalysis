package com.example.autoimpactanalysis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.autoimpactanalysis.common.Constants;
import com.example.autoimpactanalysis.entity.VO.UserVO;
import com.example.autoimpactanalysis.entity.User;
import com.example.autoimpactanalysis.exception.ServiceException;
import com.example.autoimpactanalysis.mapper.UserMapper;
import com.example.autoimpactanalysis.service.IUserService;
import com.example.autoimpactanalysis.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @ClassName: UserServiceImpl
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: User 服务实现类
 */
@Slf4j
@Service
@DS("mysql")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserVO login(UserVO userVO) {
        User one = getUserInfo(userVO);
        if (one != null) {
            //复制对象的属性
            BeanUtil.copyProperties(one, userVO, true);//ignoreCase 忽略大小写
            log.info(one.toString());
            log.info(userVO.toString());
            // 设置token
            String token = TokenUtils.generatorToken(one.getUserId(),one.getPassword());
            userVO.setToken(token);
            log.info("token:"+token);
            return userVO;
        } else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    //不通过数据库验证用户
    /*@Override
    public UserDTO login(UserDTO userDTO) {
        userDTO.setNickname("管理员");
        if (userDTO != null) {
            log.info(userDTO.toString());
            // 设置token
            String token = TokenUtils.generatorToken("1",userDTO.getPassword());
            userDTO.setToken(token);
            log.info("token:"+token);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }*/

    @Override
    public User register(UserVO userVO) {
        User one = getUserInfo(userVO);
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(userVO, one, true);
            save(one);
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserVO userVO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userVO.getUserName());
        queryWrapper.eq("password", userVO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
