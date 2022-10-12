package com.example.autoimpactanalysis.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: SocketWindowJava
 * @Author: kevin
 * @Date: 2022/7/15 15:25
 * @Description: 滑动窗口计算
 * 通过sockrt模拟产生单词数据
 * flink对数据进行统计计算
 * 需实现每隔1秒对最近2秒内的数据进行汇总计算
 * @Version: V1.0
 */
@Slf4j
public class SocketWindowJava {

    public static void main(String[] args) throws Exception {

        //获取需要的端口号
        int port;
        try {
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("no port set,default port 9000");
            port = 9000;
        }

        //获取flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //主机名
        //String hostname = "localhost";
        String hostname = "192.168.8.66";
        //换行符
        String delimiter = "\n";
        //连接socket获取输入的数据
        DataStreamSource<String> text = env.socketTextStream(hostname, port, delimiter);

        //a b c =>a 1;b 1;c 1
        DataStream<WordWithCount> windowCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            public void flatMap(String value, Collector<WordWithCount> out){
                String[] splits = value.split("\\s");// \\s空格或制表符
                for (String word : splits) {
                    System.out.println(word);
                    out.collect(new WordWithCount(word, 1L));
                }

            }
        }).keyBy("word")
                .timeWindow(Time.seconds(5), Time.seconds(3))//指定时间窗口为1分钟，时间间隔30秒滑动一次 socket时使用
                .sum("count");//使用sum或者reduce都可以
                /*.reduce(new ReduceFunction<WordWithCount>() {
                    @Override
                    public WordWithCount reduce(WordWithCount a, WordWithCount b) throws Exception {
                        return new WordWithCount(a.word,a.count+b.count);
                    }
                });*/

        log.info(windowCounts.toString());
        //把数据打印到控制台并且设置并行度
        windowCounts.print().setParallelism(1);
        log.info("end word count==================");
        //一定要实现，否则程序不执行，因为flink延迟计算的，只有最后调用execute()方法的时候才会真正触发
        env.execute("Socket Window Count");
        System.out.println("exit now!");
    }

    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}

