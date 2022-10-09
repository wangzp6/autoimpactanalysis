package com.example.autoimpactanalysis.flink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WordWithCount {
    public String word;
    public int count;

    public WordWithCount() {
    }

    public WordWithCount(String word, int count) {
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
