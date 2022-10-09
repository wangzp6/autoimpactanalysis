package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * @ClassName: UserMapper
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: UserMapper 接口
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

}
