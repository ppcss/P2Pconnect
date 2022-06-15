package InternetCode;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 
/*
 *文件传输服务器端 
 * 用于接受文件
 * 
 */
public class ServerFile  {
    public void ServerFile(String path)throws IOException  {
        ServerSocket serverSocket = new ServerSocket(9998) ;   //创建ServerSocket  指定端口
        Socket client = serverSocket.accept();    //accept监听 返回一个socket
        System.out.println("Server");
        File file=new File(path);//创建接受到的文件
        InputStream inputStream = new BufferedInputStream(client.getInputStream());   //从socket里读取数据
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path)) ;   //输出到指定文件
        int len = -1 ;
        byte[] bytes = new byte[1024] ;
        while ((len = inputStream.read(bytes))!= -1){
            outputStream.write(bytes,0,len);
        }
        outputStream.close();
        inputStream.close();
        client.close();
    }
}
 