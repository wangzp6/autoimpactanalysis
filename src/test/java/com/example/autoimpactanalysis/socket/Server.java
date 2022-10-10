package com.example.autoimpactanalysis.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: Server
 * @Author: kevin
 * @Date: 2022/10/10 9:02
 * @Description: 服务端
 * @Version: V1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            log.info("服务器启动完成...监听启动！");
            //开启监听，等待客户端的访问
            Socket socket = serverSocket.accept();
            // 获取输入流，因为是客户端向服务器端发送了数据
            InputStream inputStream = socket.getInputStream();
            // 创建一个缓冲流
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String info = null;
            while ((info = br.readLine())!=null){
                log.info("这里是服务端 客户端是："+info);
            }
            //向客户端做出响应
            OutputStream outputStream = socket.getOutputStream();
            //info = "这里是服务器端，我们接受到了你的请求信息，正在处理...处理完成！";
            info = "a bb ssss dsn vfnv vsjns vsvfdjn vs a v bb vne dsn vnr";
            outputStream.write(info.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
