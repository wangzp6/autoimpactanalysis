package com.example.autoimpactanalysis.socket;

import java.io.*;
import java.net.Socket;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: Client
 * @Author: kevin
 * @Date: 2022/10/10 9:05
 * @Description: 客户端
 * @Version: V1.0
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",8080);
            OutputStream outputStream = socket.getOutputStream();
            String info = "你好啊！";
            //输出！
            outputStream.write(info.getBytes());
            socket.shutdownOutput();
            //接收服务器端的响应
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((info = br.readLine())!=null){
                System.out.println("接收到了服务端的响应！" + info);
            }
            //刷新缓冲区
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
