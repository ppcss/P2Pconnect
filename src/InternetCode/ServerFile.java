package InternetCode;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 
/*
 *�ļ������������ 
 * ���ڽ����ļ�
 * 
 */
public class ServerFile  {
    public void ServerFile(String path)throws IOException  {
        ServerSocket serverSocket = new ServerSocket(9998) ;   //����ServerSocket  ָ���˿�
        Socket client = serverSocket.accept();    //accept���� ����һ��socket
        System.out.println("Server");
        File file=new File(path);//�������ܵ����ļ�
        InputStream inputStream = new BufferedInputStream(client.getInputStream());   //��socket���ȡ����
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path)) ;   //�����ָ���ļ�
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
 