package InternetCode;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class TalkClient {
    // 创建流对象
    private ObjectOutputStream out = null;
    // 创建流对象
    private ObjectInputStream in = null;
    //声明服务器Socket
    private ServerSocket serverSocket;

    /**
     * 利用"127.0.0.1", 2022向P2P服务器发起连接
     */
    public void getConnect(){
        try { // 捕捉异常
            Socket socket;
            System.out.println("开始连接服务器");
            socket = new Socket("localhost", 2022); // 实例化Socket对象
            //客户端信息注册
            ClientAbleUtil ableUtil=new ClientAbleUtil();
            ableUtil.sendObject(makeActiveClientInfo(),socket);
            //选择用户连接
            connectP(socket,ableUtil);
        } catch (Exception e) {
            e.printStackTrace(); // 输出异常信息
        }
    }

    //编辑注册表中的信息指向可以连接到我的地址 
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
     * 注册在服务器中用于联系的服务器
     */
    public void getServer() {
        try {
            serverSocket = new ServerSocket(1998);
            while (true) {
                Socket socket;
                // 监听是否有客户端连接
                System.out.println("等待连接！！");
                socket = serverSocket.accept();
                System.out.println("连接成功！");
                ClientAbleUtil ableUtil=new ClientAbleUtil();
                // 创建并启动连接线程对象
                ClientThread clientThread=new ClientThread(socket,ableUtil,socket.getInetAddress().getHostName()+"向您发起会话");
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 输出异常信息
        }
    }
    
    
    /**
     * 连接服务器进行查询
     * @socket p2p服务器
     */
    public void connectP(Socket socket,ClientAbleUtil ableUtil) {
    	String connectObj= JOptionPane.showInputDialog("对方客户端名称");
        ClientInfo clientInfo=new ClientInfo();
        clientInfo.setClientId(connectObj);
        clientInfo.setQuery("Y");
        System.out.println("开始向服务器发送查询信息");
        ableUtil.sendObject(clientInfo,socket);
        //等待接收服务器发送来的客户信息
        try{
            clientInfo=ableUtil.getClientInfoFromServer(socket);
        }catch (Exception e3){
            e3.printStackTrace();
        }
        if(clientInfo.isActive().equals("Y")){
            //连接通信对方
            try {
                Socket clientSocket = new Socket(clientInfo.getIp(),Integer.parseInt(clientInfo.getPort()));
                ClientAbleUtil ableUtil1=new ClientAbleUtil();
                new ClientThread(clientSocket,ableUtil1,"连接成功！！可以开始通信了\n").start();
            } catch (Exception ex) {
                ex.printStackTrace(); // 输出异常信息
            }
        }else if(clientInfo.isActive().equals("N")){
            //对方不在线，输出提示信息
        	JOptionPane.showMessageDialog(null,"对方不在线，请稍后在试！！");
        }else{
        	JOptionPane.showMessageDialog(null,"不存在此用户！！！请检查");
        }
    }
    

    public static void main(String[] args) {
       TalkClient client=new TalkClient();
       //连接服务器线程
       new Thread(new Runnable() {
           @Override
           public void run() {
               client.getConnect();
           }
       }).start();
       //监听连接线程
       new Thread(new Runnable() {
           @Override
           public void run() {
               client.getServer();
           }
       }).start();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}

