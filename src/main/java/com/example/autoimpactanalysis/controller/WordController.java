package com.example.autoimpactanalysis.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: WordController
 * @Author: kevin
 * @Date: 2022/10/23 18:11
 * @Description: TODO
 * @Version: V1.0
 */
public class WordController {
    public void preview(HttpServletResponse response) throws Exception {

        byte[] data = null;

        FileInputStream input = new FileInputStream(new File("文件路径"));
        data = new byte[input.available()];
        input.read(data);
        response.getOutputStream().write(data);
        input.close();

    }
}
