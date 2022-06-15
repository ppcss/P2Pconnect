package InternetCode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientAbleUtil {

    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private PrintWriter infoOut;
    private BufferedReader infoIn;
    private DataOutputStream DataOut;
    private DataInputStream DataIn;


    /**
     * 发送信息
     * @param info
     * @param socket
     */
    public void sendInfo(String info, Socket socket){
        try{
            if(infoOut==null){
                infoOut=new PrintWriter(socket.getOutputStream(),true);
            }
            infoOut.println(info);
            System.out.println("发送消息完成: "+info+"发送到："+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送对象
     * @param obj 要发送的对象
     * @param socket 连接套接字
     */
   public void sendObject(Object obj, Socket socket){
        try {
            if(objectOut==null){
                objectOut=new ObjectOutputStream(socket.getOutputStream());
            }
            objectOut.writeObject(obj);
            System.out.println("发送对象完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 监听接收服务端返回的对象信息
     * @param socket
     * @return
     */
    public ClientInfo getClientInfoFromServer(Socket socket) throws IOException {
        try {
            if(socket!=null){
                if(objectIn==null){
                    objectIn=new ObjectInputStream(socket.getInputStream());
                }
                while (true){
                    ClientInfo clientInfo=(ClientInfo) objectIn.readObject();
                    if(clientInfo!=null){
                        System.out.println("接收对象完成");
                        //System.out.println("返回的对象信息"+clientInfo);
                        return clientInfo;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            objectIn.close();
        }
        return null;
    }

    /**
     * 监听其他节点传过来的信息
     * @param socket
     * @return
     */
    public  String getInfo(Socket socket){
        String info=null;
        try {
            if(socket!=null){
                if(infoIn==null){
                    infoIn=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
                while (true){
                    info=infoIn.readLine();
                    if(info!=null){
                        System.out.println("接收消息完成:"+info);
                        return info;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}


