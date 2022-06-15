package InternetCode;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class TalkClient {
    // ����������
    private ObjectOutputStream out = null;
    // ����������
    private ObjectInputStream in = null;
    //����������Socket
    private ServerSocket serverSocket;

    /**
     * ����"127.0.0.1", 2022��P2P��������������
     */
    public void getConnect(){
        try { // ��׽�쳣
            Socket socket;
            System.out.println("��ʼ���ӷ�����");
            socket = new Socket("localhost", 2022); // ʵ����Socket����
            //�ͻ�����Ϣע��
            ClientAbleUtil ableUtil=new ClientAbleUtil();
            ableUtil.sendObject(makeActiveClientInfo(),socket);
            //ѡ���û�����
            connectP(socket,ableUtil);
        } catch (Exception e) {
            e.printStackTrace(); // ����쳣��Ϣ
        }
    }

    //�༭ע����е���Ϣָ��������ӵ��ҵĵ�ַ 
    public static ClientInfo makeActiveClientInfo() throws UnknownHostException {
        InetAddress inetAdder = InetAddress.getLocalHost();
        ClientInfo clientInfo=new ClientInfo();
        clientInfo.setClientId(String.valueOf(System.currentTimeMillis()));
        clientInfo.setActive("Y");
        clientInfo.setIp(inetAdder.getHostAddress());
        clientInfo.setPort("1998");
        clientInfo.setQuery("N");
        return clientInfo;
    }


	/**
     * ע���ڷ�������������ϵ�ķ�����
     */
    public void getServer() {
        try {
            serverSocket = new ServerSocket(1998);
            while (true) {
                Socket socket;
                // �����Ƿ��пͻ�������
                System.out.println("�ȴ����ӣ���");
                socket = serverSocket.accept();
                System.out.println("���ӳɹ���");
                ClientAbleUtil ableUtil=new ClientAbleUtil();
                // ���������������̶߳���
                ClientThread clientThread=new ClientThread(socket,ableUtil,socket.getInetAddress().getHostName()+"��������Ự");
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace(); // ����쳣��Ϣ
        }
    }
    
    
    /**
     * ���ӷ��������в�ѯ
     * @socket p2p������
     */
    public void connectP(Socket socket,ClientAbleUtil ableUtil) {
    	String connectObj= JOptionPane.showInputDialog("�Է��ͻ�������");
        ClientInfo clientInfo=new ClientInfo();
        clientInfo.setClientId(connectObj);
        clientInfo.setQuery("Y");
        System.out.println("��ʼ����������Ͳ�ѯ��Ϣ");
        ableUtil.sendObject(clientInfo,socket);
        //�ȴ����շ������������Ŀͻ���Ϣ
        try{
            clientInfo=ableUtil.getClientInfoFromServer(socket);
        }catch (Exception e3){
            e3.printStackTrace();
        }
        if(clientInfo.isActive().equals("Y")){
            //����ͨ�ŶԷ�
            try {
                Socket clientSocket = new Socket(clientInfo.getIp(),Integer.parseInt(clientInfo.getPort()));
                ClientAbleUtil ableUtil1=new ClientAbleUtil();
                new ClientThread(clientSocket,ableUtil1,"���ӳɹ��������Կ�ʼͨ����\n").start();
            } catch (Exception ex) {
                ex.printStackTrace(); // ����쳣��Ϣ
            }
        }else if(clientInfo.isActive().equals("N")){
            //�Է������ߣ������ʾ��Ϣ
        	JOptionPane.showMessageDialog(null,"�Է������ߣ����Ժ����ԣ���");
        }else{
        	JOptionPane.showMessageDialog(null,"�����ڴ��û�����������");
        }
    }
    

    public static void main(String[] args) {
       TalkClient client=new TalkClient();
       //���ӷ������߳�
       new Thread(new Runnable() {
           @Override
           public void run() {
               client.getConnect();
           }
       }).start();
       //���������߳�
       new Thread(new Runnable() {
           @Override
           public void run() {
               client.getServer();
           }
       }).start();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}

