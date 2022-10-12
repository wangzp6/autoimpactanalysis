package com.example.autoimpactanalysis.flink

/**
 * @project_name: autoimpactanalysis
 * @ClassName: BatchFileWindowScala
 * @Author: kevin
 * @Date: 2022/7/27 11:13
 * @Description: TODO
 * @Version: V1.0
 */
object BatchFileWindowScala {
  def main(args: Array[String]): Unit = {

    //注意，必须添加这一句，隐式转换，否则flatmap方法执行报错
    import org.apache.flink.api.scala._

    //获取运行环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    //获取数据
    val filePath: String = "D:\\work\\workspace\\work_workspace\\autoimpactanalysis\\src\\main\\resources\\file"
    val output: String = "D:\\work\\workspace\\work_workspace\\autoimpactanalysis\\src\\main\\resources\\resultScala"
    //从文件中获取
    val text = env.readTextFile(filePath)

    //解析数据（把数据打平），分析，窗口计算，并且聚合求sum
    val Counts = text.flatMap(_.toLowerCase.split("\\W+"))//把每一行单词切开
      .filter(_.nonEmpty)
      //.map(w=>(w,1))//打平 把单词转成Word，1这种形式
      .map((_,1))//打平 把单词转成Word，1这种形式 简写
      .groupBy(0)//分组
      .sum(1)//sum或者reduce

    //输出到csv,设置并行度
    Counts.writeAsCsv(output,"\n"," ").setParallelism(1)
    //执行任务
    env.execute("Batch Window Count")
  }
}
