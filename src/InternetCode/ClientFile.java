package InternetCode;

import java.io.*;
import java.net.Socket;
 
/**
 * �ļ�����ͻ���
 * �������ļ�
 */
public class ClientFile {
    public void ClientFile(String path) throws IOException {
        Socket socket = new Socket("localhost",9998) ;    //����Socket
        InputStream inputStream  = new BufferedInputStream(new FileInputStream(path)) ;   //���ڿͻ�����˵�����ļ���Input
        OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream()) ;   //�ͻ��˽��ļ������socket��
        int len  = -1 ;
        byte[] bytes  = new byte[1024] ;  
            while ((len = inputStream.read(bytes))!=-1){    //�������뵽bytes
            outputStream.write(bytes,0,len);   //bytes����д��socket
        }
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}