package InternetCode;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ServerThread extends Thread{
    // ���������
    private ObjectOutputStream out = null;
    // ����������
    private ObjectInputStream in = null;
    Socket socket;
    //�ͻ���ע���
    Map<String,ClientInfo> registerMaps;

    public ServerThread(Socket socket, Map<String,ClientInfo>registerMaps) throws IOException {
        this.socket = socket;
        this.registerMaps=registerMaps;
        in=new ObjectInputStream(socket.getInputStream());
        out=new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        ClientInfo clientInfo = null;
        try {
            while (true){
                clientInfo=(ClientInfo) in.readObject();
                if(clientInfo!=null){
                    if(clientInfo.isQuery().equals("N")){
                        registerMaps.put(clientInfo.getClientId(),clientInfo);
                        System.out.println("ע��ɹ���");
                        System.out.println(clientInfo.toString());
                    }else{
                        ClientInfo clientInfo1=registerMaps.get(clientInfo.getClientId());
                        if(clientInfo1!=null){
                            out.writeObject(clientInfo1);
                            System.out.println("��ѯ��ɣ�");
                            System.out.println(registerMaps.get(clientInfo.getClientId()).toString());
                        }else{
                            clientInfo1=new ClientInfo();
                            clientInfo1.setActive("F");
                            out.writeObject(clientInfo1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(socket + "�Ͽ����ӡ�\n");
        }
    }
}
