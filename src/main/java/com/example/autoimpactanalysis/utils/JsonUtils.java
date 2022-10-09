package com.example.autoimpactanalysis.utils;

import java.io.*;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: JsonUtils
 * @Author: kevin
 * @Date: 2022/6/17 11:07
 * @Description: TODO
 * @Version: V1.0
 */
public class JsonUtils {

    //把一个文件中的内容读取成一个String字符串
    public static String getStr(File jsonFile){
        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
