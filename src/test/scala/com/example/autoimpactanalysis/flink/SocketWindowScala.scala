package com.example.autoimpactanalysis.flink

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @project_name: autoimpactanalysis
 * @ClassName: SocketWindowScala
 * @Author: kevin
 * @Date: 2022/7/27 11:13
 * @Description: TODO
 * @Version: V1.0
 */
object SocketWindowScala {
  def main(args: Array[String]): Unit = {

    //注意，必须添加这一句，隐式转换，否则flatmap方法执行报错
    import org.apache.flink.api.scala._

    //获取运行环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //获取socket端口号
    var port: Int = try {
      ParameterTool.fromArgs(args).getInt("port")
    }catch {
      case e:  Exception=>{
        System.err.println("no port set,default port 9000--scala")
      }
        9000
    }
    //主机名
    val hostname = "localhost"
    //换行符
    val delimiter = '\n'
    //连接socket获取输入的数据
    val text = env.socketTextStream(hostname, port, delimiter)

    //解析数据（把数据打平），分析，窗口计算，并且聚合求sum
    val windowCounts = text.flatMap(line => line.split("\\s"))//打平，把每一行单词切开
      .map(w=>WordWithCount(w,1))//把单词转成Word，1这种形式
      .keyBy("word")//分组
      .timeWindow(Time.seconds(2),Time.seconds(1))//指定窗口大小，指定间隔时间
      .sum("count")//sum或者reduce
    //      .reduce((a,b)=>WordWithCount(a.word,a.count+b.count))

    windowCounts.print().setParallelism(1)//打印到控制台
    //执行任务
    env.execute("Socket Window Count--scala")
  }

  case class WordWithCount(word:String,count:Long)

}
