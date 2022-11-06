package com.example.autoimpactanalysis.mapper;

import com.example.autoimpactanalysis.entity.ProjectDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ProjectDetailMapper
 * @Author: kevin
 * @Date: 2022-10-18
 * @Version: V1.0
 * @Description: ProjectDetailMapper 接口
 */
@Mapper
@Component
public interface ProjectDetailMapper extends BaseMapper<ProjectDetail> {

}
