package com.example.autoimpactanalysis.utils.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: CodeGenerator
 * @Author: kevin
 * @Date: 2022/6/14 09:31
 * @Description: 代码生成器
 * @Version: V1.0
 */
public class MysqlCodeGenerator {
    public static void main(String[] args) {
        generator();
    }
    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/developdatabase?serverTimezone=GMT%2b8", "root", "root123456")
                .globalConfig(builder -> {
                    builder.author("kevin") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("src\\main\\java\\")// 指定输出目录
                            .dateType(DateType.ONLY_DATE);
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
                    builder.addInclude("document_detail") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_") // 设置过滤表前缀
                            .addTableSuffix("_20220620","_backup");//设置过滤表后缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
