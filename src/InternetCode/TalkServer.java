package InternetCode;



import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TalkServer {
    // ����ServerSocket����
    private ServerSocket server;
    // ����Socket����socket
    private Socket socket;
    //�ͻ���ע���
    private Map<String, ClientInfo> registerMaps;

    /**
     * �½����������󲢵ȴ�����
     */
    public void getServer() {
        try {
            server = new ServerSocket(2022);
            registerMaps=new HashMap<>();
            while (true) {
                // �����Ƿ��пͻ�������
                System.out.println("�����������ɹ����ȴ�����");
                socket = server.accept();
                System.out.println("���ӳɹ�");
                // ���������������̶߳���
                new ServerThread(socket,registerMaps).start();
            }
        } catch (Exception e) {
            e.printStackTrace(); // ����쳣��Ϣ
        }
    }

    public static void main(String[] args) {
        TalkServer talkServer=new TalkServer();
        talkServer.getServer();
    }
}
