package com.example.autoimpactanalysis.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * @project_name: autoimpactanalysis
 * @ClassName: BatchFileWindowJava
 * @Author: kevin
 * @Date: 2022/7/15 15:25
 * @Description: 滑动窗口计算
 * 通过sockrt模拟产生单词数据
 * flink对数据进行统计计算
 * 需实现每隔1秒对最近2秒内的数据进行汇总计算
 * @Version: V1.0
 */
@Slf4j
public class BatchFileWindowJava {

    @Test
    public void count() throws Exception {
        //获取flink的运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //设置并行度
        env.setParallelism(1);

        //以文件方式获取数据
        String filePath = "src\\main\\resources\\files\\data";
        String outPath = "src\\main\\resources\\files\\result\\output";
        //打印文件内容
        /*File file = new File(filePath);
        log.info(txt2String(file));*/
        //从文件中获取
        DataSource<String> text = env.readTextFile(filePath);
        //a b c =>a 1;b 1;c 1
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer()).groupBy(0).sum(1);

        //把数据输出到csv
        counts.writeAsCsv(outPath,"\n"," ");//行分隔符\n,字段分隔符" "
        //一定要实现，否则程序不执行，因为flink延迟计算的，只有最后调用execute()方法的时候才会真正触发
        env.execute("batch word count");
        System.out.println("exit now!");
    }

    public static class Tokenizer implements FlatMapFunction<String, Tuple2<String,Integer>>{
        public void flatMap(String value, Collector<Tuple2<String,Integer>> out) throws Exception {
            String[] tokens = value.toLowerCase().split("\\W+");// \W+ 非任意字符 空格或制表符
            for (String token : tokens) {
                if(token.length()>0){
                    System.out.println(token);
                    out.collect(new Tuple2<String,Integer>(token, 1));
                }
            }
        }
    }

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append("," + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString().substring(1);
    }
}

