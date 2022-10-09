package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.Broomdatascript;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: BroomdatascriptMapper
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomdatascriptMapper 接口
 */
@Mapper
@Repository
public interface BroomdatascriptMapper extends BaseMapper<Broomdatascript> {
    /**
     * 静态查询
     */
//    @Select("SELECT * " +
//            " FROM broomdatascript, sys_files " +
//            " WHERE broomdatascript.bdcid = #{bdcid} " +
//            "     AND broomdatascript.bdcid = sys_files.bdcid")
//    String findBroomdatascript(@Param("bdcid") Integer bdcid);

    /**
     * 动态查询
     */
//    @Select("SELECT * " +
//            " FROM broomdatascript, sys_files " +
//            " ${ew.customSqlSegment} ")
//    IPage<Broomdatascript> findBroomdatascript(IPage<Broomdatascript> page, @Param("ew") QueryWrapper<Broomdatascript> queryWrapper);

    @Select("SELECT * " +
            " FROM broomdatascript" +
            " ${ew.customSqlSegment} ")
    IPage<Broomdatascript> findBroomdatascript(IPage<Broomdatascript> page, @Param("ew") QueryWrapper<Broomdatascript> queryWrapper);
}
