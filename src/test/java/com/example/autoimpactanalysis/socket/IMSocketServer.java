package com.example.autoimpactanalysis.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: IMSocketServer
 * @Author: kevin
 * @Date: 2022/10/10 9:35
 * @Description: 即时消息服务器
 * @Version: V1.0
 */
@Slf4j
public class IMSocketServer {
    private static int PORT = 9000;
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // 开启端口
            server = new ServerSocket(PORT);
            log.info("服务器已启动");
            // 与客户端建立socket连接
            Socket socket = server.accept();
            log.info("客户端请求，创建连接成功");
            // 写出器
            PrintWriter writer2Client = new PrintWriter(socket.getOutputStream());
            writer2Client.write("连接创建成功\r\n");
            writer2Client.flush();
            // 客户端读入器
            Scanner scanner4Client = new Scanner(socket.getInputStream());
            // 控制台读入器
            Scanner scanner4Console = new Scanner(System.in);
            // 等待客户端输入
            while(true) {
                // 读取客户端消息并打印
                String msg4Client = scanner4Client.nextLine();
                log.info("客户端：" + msg4Client);
                System.out.print("服务端：");
                // 获取控制台输入数据并发送到客户端
                String msg4Console = scanner4Console.nextLine();
                writer2Client.write(msg4Console + "\r\n");
                writer2Client.flush();
            }
        } catch (IOException e) {
            log.error("打开端口socket失败，端口=" + PORT + ", 异常信息=" + e.getMessage());
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
