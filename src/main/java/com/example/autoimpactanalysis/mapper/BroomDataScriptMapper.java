package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.autoimpactanalysis.entity.BroomDataScript;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BroomDataScriptMapper
 * @Author: kevin
 * @Date: 2022-06-24
 * @Version: V1.0
 * @Description: BroomDataScriptMapper 接口
 */
@Mapper
@Repository
@Component
public interface BroomDataScriptMapper extends BaseMapper<BroomDataScript> {
    /**
     * 静态查询
     */
//    @Select("SELECT * " +
//            " FROM broomdatascript, sys_files " +
//            " WHERE broomdatascript.bdcid = #{bdcid} " +
//            "     AND broomdatascript.bdcid = sys_files.bdcid")
//    String findBroomdatascript(@Param("bdcid") String bdcid);

    /**
     * 动态查询
     */
//    @Select("SELECT * " +
//            " FROM broomdatascript, sys_files " +
//            " ${ew.customSqlSegment} ")
//    IPage<Broomdatascript> findBroomdatascript(IPage<Broomdatascript> page, @Param("ew") QueryWrapper<Broomdatascript> queryWrapper);

    @Select("SELECT * " +
            " FROM broom_data_script" +
            " ${ew.customSqlSegment} ")
    IPage<BroomDataScript> findBroomDataScript(IPage<BroomDataScript> page, @Param("ew") QueryWrapper<BroomDataScript> queryWrapper);
}
