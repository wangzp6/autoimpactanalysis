package com.example.autoimpactanalysis.utils.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @project_name: springbootbai
 * @ClassName: CodeGenerator
 * @Author: kevin
 * @Date: 2022/3/8 13:59
 * @Description: 代码生成器
 * @Version: V1.0
 */
public class EdwCodeGenerator {
    public static void main(String[] args) {
        generator();
    }
    private static void generator(){
        FastAutoGenerator.create("jdbc:oracle:thin:@22.21.49.39:11521/edwsit", "xyd", "!_GBr1kPM~m")
                .globalConfig(builder -> {
                    builder.author("kevin") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.autoimpactanalysis") // 设置父包名
                            .moduleName(null) // 设置父包模块名 若为""则多一个/
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();//在UserMapper 加@mapper注释，在 MybatisPlusConfig已经加过了，可加可不加
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("ALL_SOURCE_BACKUP") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_","T_APS_","ALL_") // 设置过滤表前缀
                            .addTableSuffix("_20220315", "_BACKUP"); // 设置过滤表后缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
