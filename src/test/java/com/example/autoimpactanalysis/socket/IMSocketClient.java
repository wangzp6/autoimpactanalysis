package com.example.autoimpactanalysis.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: IMSocketClient
 * @Author: kevin
 * @Date: 2022/10/10 9:37
 * @Description: 即时消息客户端
 * @Version: V1.0
 */
@Slf4j
public class IMSocketClient {
    private static int PORT = 13;
    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            // 与服务器端建立socket连接
            socket.connect(new InetSocketAddress("localhost", PORT), 3000);
            // 新建读取器，写出器
            Scanner scanner4Server = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            // 控制台读入器
            Scanner scanner4Console = new Scanner(System.in);
            log.info("连接成功，等待服务器响应");
            // 等待服务器控制台输入
            while(true) {
                // 读取服务器响应并打印
                String msg4Server = scanner4Server.next();
                log.info("服务器：" + msg4Server);
                System.out.printf("客户端：");
                // 客户端读取控制台输入，并发送到服务器
                String msg4Console = scanner4Console.nextLine();
                printWriter.write(msg4Console + "\r\n");
                printWriter.flush();
            }
        } catch (Exception e) {
            log.error("客户端通讯失败, 异常信息=" + e.getMessage());
        }
    }
}
