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
     * ������Ϣ
     * @param info
     * @param socket
     */
    public void sendInfo(String info, Socket socket){
        try{
            if(infoOut==null){
                infoOut=new PrintWriter(socket.getOutputStream(),true);
            }
            infoOut.println(info);
            System.out.println("������Ϣ���: "+info+"���͵���"+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ���Ͷ���
     * @param obj Ҫ���͵Ķ���
     * @param socket �����׽���
     */
   public void sendObject(Object obj, Socket socket){
        try {
            if(objectOut==null){
                objectOut=new ObjectOutputStream(socket.getOutputStream());
            }
            objectOut.writeObject(obj);
            System.out.println("���Ͷ������");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * �������շ���˷��صĶ�����Ϣ
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
                        System.out.println("���ն������");
                        //System.out.println("���صĶ�����Ϣ"+clientInfo);
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
     * ���������ڵ㴫��������Ϣ
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
                        System.out.println("������Ϣ���:"+info);
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


