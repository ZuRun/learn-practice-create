package cn.zull.lpc.learn.basic.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zurun
 * @date 2020/7/20 18:43:00
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        ServerSocket socket =new ServerSocket(9090);
        Socket accept = socket.accept();
        InputStream inputStream = accept.getInputStream();
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        //这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
        String clientInputStr = input.readLine();
        // 处理客户端数据
        System.out.println("客户端发过来的内容:" + clientInputStr);

//        // 向客户端回复信息
//        PrintStream out = new PrintStream(socket.getOutputStream());
//        System.out.print("请输入:\t");
//        // 发送键盘输入的一行
//        String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
//        out.println(s);
    }
}