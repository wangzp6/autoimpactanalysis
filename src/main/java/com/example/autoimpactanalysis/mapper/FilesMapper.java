package com.example.autoimpactanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.autoimpactanalysis.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @ClassName: FilesMapper
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: FilesMapper 接口
 */
@Mapper
@Component
public interface FilesMapper extends BaseMapper<Files> {

    @Select("select * from sys_files where bdc_id = #{bdcId} and is_delete = 0 and enable = 1")
    ArrayList<Files> findByBdcId(Integer bdcId);

    @Select("select * from sys_files where url like '%${fileUUID}%' and is_delete = 0 and enable = 1")
    ArrayList<Files> findByUrl(String fileUUID);

}
