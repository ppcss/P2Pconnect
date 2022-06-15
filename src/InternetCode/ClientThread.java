package InternetCode;



import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    //连接的套接字
    Socket socket;
    ClientAbleUtil ableUtil;
    ClientAbleUtil ableUtil1;
    //是否客户端标志
    String clientFlat;
    String text;
    // 创建JTextArea对象
    public JTextArea outInfo = new JTextArea(12,35);//长宽
    //文件选择
    int option;
    String pathSend;
    String pathRecv;

    public ClientThread(Socket socket,ClientAbleUtil ableUtil,String text) throws IOException {
        this.socket = socket;
        this.ableUtil=ableUtil;

        this.text=text;
    }

    public void run() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //运行图像化界面
                    Frame frame=new Frame(text);
                    frame.setVisible(true);
                }
            }).start();
            //监听发送的消息
            new Thread(new Runnable(){
                @Override
                public void run() {
                    while (true){
                        String line=ableUtil.getInfo(socket);
                        if(line!=null){
                            outInfo.append("\n");
                            outInfo.append(socket.getInetAddress().getHostName()+"发送消息:"+line); 
                            System.out.println("消息内容："+line);
                            }
 
                           if(line.equals("拒绝接收")) {
                    	   JOptionPane.showMessageDialog(null,"对方拒绝接收文件");
                       }
                       if(line.indexOf("请发送")!=-1) {
                    	   JOptionPane.showMessageDialog(null, "开始传输文件", "文件传输", 
                    			   JOptionPane. ERROR_MESSAGE);
                    	   ClientFile sendFile=new ClientFile();
                    	   try {
							sendFile.ClientFile(pathSend);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	   JOptionPane.showMessageDialog(null, "文件传输成功", "文件传输", 
                    			   JOptionPane. ERROR_MESSAGE);
                       }  	
                       
                       if(line.indexOf("请接收文件")!=-1) {
                       	
                       	pathSend=line.substring(line.indexOf(":")+1);
                       	
                       	int sl = JOptionPane.showOptionDialog(null, "您收文件:"+pathSend+"，是否接收",
                       			"文件传输", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null,
                       			new String[]{"接受","拒绝"}, "");
                       	if(sl==0) {
                       		//开启一个新的套接字,接受文件
                       		ableUtil.sendInfo("请发送",socket);
                       		pathRecv= JOptionPane.showInputDialog("文件地址",pathSend);
                       		ServerFile recFile=new ServerFile();
                       		try {
									recFile.ServerFile(pathRecv);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                       	}else {
                       		ableUtil.sendInfo("拒绝接收",socket);
                       	}
                       } 
                    }
                }
            }).start();
           

        } catch (Exception e) {
            System.out.println(socket + "已经退出。\n");
        }
    }
    
    class Frame extends JFrame{
        //连接客户端文本框
        private JTextField connectJText;
        //发送信息文本狂
        private JTextArea sendInfoJText;//长宽;

        public Frame(String text) {
            super();
            setTitle("客户端程序");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(500, 100, 415, 545);
            
            //聊天信息框
            JScrollPane scrollPane = new JScrollPane(outInfo,JScrollPane.
            		VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//高，宽
            
            scrollPane.setViewportView(outInfo);
            

            //文件选择
            JButton jButton=new JButton("选择文件");
            JTextField SelsctPath = getJTextFiled(255,26);//宽长
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                	JFileChooser jfchooser = new JFileChooser();
                	jfchooser.setDialogTitle("选择文件");
    				option = jfchooser.showOpenDialog(null);
    				pathSend = jfchooser.getSelectedFile().getPath();
    				SelsctPath.setText(pathSend);
                }	
            });
            
            JPanel panel1;
            panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel1.add(getLabel("选择要发送的文件:"));
            panel1.add(SelsctPath);
            
            JPanel panel2;
            panel2 = new JPanel(new FlowLayout(22));
            panel2.add(jButton);
            panel2.add(getButton("发送文件"));
           
            JPanel panel0;
            panel0 = new JPanel();
            panel0.setPreferredSize(new Dimension(410,530));
            panel0.add(scrollPane);
            panel0.add(getSendInfoPanel());
            panel0.add(panel1);
            panel0.add(panel2);
            
            getContentPane().add(panel0);
            outInfo.append(text);
                 
            
        }

        /**得到按钮对象
         * @return 按钮对象
         */
        protected JButton getButton(String text) {
            JButton jButton=new JButton();
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {

                    //发送消息给对方
                    if(text.equals("发送")){
                       ableUtil.sendInfo(sendInfoJText.getText(),socket);
                    }
                    if(text.equals("发送文件")){
                            ableUtil.sendInfo("请接收文件:"+pathSend,socket);
                    }
                }
            });
            jButton.setText(text);

            return jButton;
        }

        /**
         * 得到发送信息的画板
         * @return
         */
                     
        protected JPanel getSendInfoPanel() {
            JPanel panel;
            sendInfoJText = new JTextArea(4,29);
            JScrollPane sendInfo = new JScrollPane(sendInfoJText,JScrollPane.
            		VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setPreferredSize(new Dimension(392,135));
            panel.add(getLabel("请输入要发送的信息:"));
            panel.add(sendInfo);
            panel.add(getButton("发送"));
            return panel;
        }

        /**文本标签
         * @return
         */
        protected JLabel getLabel(String text) {
            JLabel jLabel=new JLabel();
            jLabel.setText(text);
            return jLabel;
        }

        /**获得输入框
         * @param width
         * @param height
         * @return 输入框对象
         */
        protected JTextField getJTextFiled(int width,int height) {
            JTextField jTextField;
            jTextField = new JTextField();
            jTextField.setPreferredSize(new Dimension(width, height));
            return jTextField;
        }

    }


}

