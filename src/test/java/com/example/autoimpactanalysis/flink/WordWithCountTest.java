package com.example.autoimpactanalysis.flink;

import com.example.autoimpactanalysis.controller.BroomdatascriptController;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: WordWithCountTest
 * @Author: kevin
 * @Date: 2022/7/26 13:53
 * @Description: TODO
 * @Version: V1.0
 */
@Slf4j
public class WordWithCountTest {

    @Test
    public void count(){
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        String path = "D:\\work\\workspace\\work_workspace\\autoimpactanalysis\\src\\main\\resources\\data.txt";
        DataSet<String> stringDataSource = executionEnvironment.readTextFile(path);
        DataSet<Tuple2<String, Integer>> set =
                stringDataSource.flatMap(new myFlatMap())
                        .groupBy(0)
                        .sum(1);
        try {
            set.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class myFlatMap implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] words = s.split(" ");
            for (String word : words) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }

}
