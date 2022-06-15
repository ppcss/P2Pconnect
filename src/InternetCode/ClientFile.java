package InternetCode;

import java.io.*;
import java.net.Socket;
 
/**
 * 文件传输客户端
 * 负责传输文件
 */
public class ClientFile {
    public void ClientFile(String path) throws IOException {
        Socket socket = new Socket("localhost",9998) ;    //创建Socket
        InputStream inputStream  = new BufferedInputStream(new FileInputStream(path)) ;   //对于客户端来说输入文件用Input
        OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream()) ;   //客户端讲文件输出到socket中
        int len  = -1 ;
        byte[] bytes  = new byte[1024] ;  
            while ((len = inputStream.read(bytes))!=-1){    //内容输入到bytes
            outputStream.write(bytes,0,len);   //bytes内如写入socket
        }
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}