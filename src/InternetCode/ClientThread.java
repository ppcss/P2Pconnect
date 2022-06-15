package InternetCode;



import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    //���ӵ��׽���
    Socket socket;
    ClientAbleUtil ableUtil;
    ClientAbleUtil ableUtil1;
    //�Ƿ�ͻ��˱�־
    String clientFlat;
    String text;
    // ����JTextArea����
    public JTextArea outInfo = new JTextArea(12,35);//����
    //�ļ�ѡ��
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
                    //����ͼ�񻯽���
                    Frame frame=new Frame(text);
                    frame.setVisible(true);
                }
            }).start();
            //�������͵���Ϣ
            new Thread(new Runnable(){
                @Override
                public void run() {
                    while (true){
                        String line=ableUtil.getInfo(socket);
                        if(line!=null){
                            outInfo.append("\n");
                            outInfo.append(socket.getInetAddress().getHostName()+"������Ϣ:"+line); 
                            System.out.println("��Ϣ���ݣ�"+line);
                            }
 
                           if(line.equals("�ܾ�����")) {
                    	   JOptionPane.showMessageDialog(null,"�Է��ܾ������ļ�");
                       }
                       if(line.indexOf("�뷢��")!=-1) {
                    	   JOptionPane.showMessageDialog(null, "��ʼ�����ļ�", "�ļ�����", 
                    			   JOptionPane. ERROR_MESSAGE);
                    	   ClientFile sendFile=new ClientFile();
                    	   try {
							sendFile.ClientFile(pathSend);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	   JOptionPane.showMessageDialog(null, "�ļ�����ɹ�", "�ļ�����", 
                    			   JOptionPane. ERROR_MESSAGE);
                       }  	
                       
                       if(line.indexOf("������ļ�")!=-1) {
                       	
                       	pathSend=line.substring(line.indexOf(":")+1);
                       	
                       	int sl = JOptionPane.showOptionDialog(null, "�����ļ�:"+pathSend+"���Ƿ����",
                       			"�ļ�����", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null,
                       			new String[]{"����","�ܾ�"}, "");
                       	if(sl==0) {
                       		//����һ���µ��׽���,�����ļ�
                       		ableUtil.sendInfo("�뷢��",socket);
                       		pathRecv= JOptionPane.showInputDialog("�ļ���ַ",pathSend);
                       		ServerFile recFile=new ServerFile();
                       		try {
									recFile.ServerFile(pathRecv);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                       	}else {
                       		ableUtil.sendInfo("�ܾ�����",socket);
                       	}
                       } 
                    }
                }
            }).start();
           

        } catch (Exception e) {
            System.out.println(socket + "�Ѿ��˳���\n");
        }
    }
    
    class Frame extends JFrame{
        //���ӿͻ����ı���
        private JTextField connectJText;
        //������Ϣ�ı���
        private JTextArea sendInfoJText;//����;

        public Frame(String text) {
            super();
            setTitle("�ͻ��˳���");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(500, 100, 415, 545);
            
            //������Ϣ��
            JScrollPane scrollPane = new JScrollPane(outInfo,JScrollPane.
            		VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//�ߣ���
            
            scrollPane.setViewportView(outInfo);
            

            //�ļ�ѡ��
            JButton jButton=new JButton("ѡ���ļ�");
            JTextField SelsctPath = getJTextFiled(255,26);//��
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                	JFileChooser jfchooser = new JFileChooser();
                	jfchooser.setDialogTitle("ѡ���ļ�");
    				option = jfchooser.showOpenDialog(null);
    				pathSend = jfchooser.getSelectedFile().getPath();
    				SelsctPath.setText(pathSend);
                }	
            });
            
            JPanel panel1;
            panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel1.add(getLabel("ѡ��Ҫ���͵��ļ�:"));
            panel1.add(SelsctPath);
            
            JPanel panel2;
            panel2 = new JPanel(new FlowLayout(22));
            panel2.add(jButton);
            panel2.add(getButton("�����ļ�"));
           
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

        /**�õ���ť����
         * @return ��ť����
         */
        protected JButton getButton(String text) {
            JButton jButton=new JButton();
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {

                    //������Ϣ���Է�
                    if(text.equals("����")){
                       ableUtil.sendInfo(sendInfoJText.getText(),socket);
                    }
                    if(text.equals("�����ļ�")){
                            ableUtil.sendInfo("������ļ�:"+pathSend,socket);
                    }
                }
            });
            jButton.setText(text);

            return jButton;
        }

        /**
         * �õ�������Ϣ�Ļ���
         * @return
         */
                     
        protected JPanel getSendInfoPanel() {
            JPanel panel;
            sendInfoJText = new JTextArea(4,29);
            JScrollPane sendInfo = new JScrollPane(sendInfoJText,JScrollPane.
            		VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setPreferredSize(new Dimension(392,135));
            panel.add(getLabel("������Ҫ���͵���Ϣ:"));
            panel.add(sendInfo);
            panel.add(getButton("����"));
            return panel;
        }

        /**�ı���ǩ
         * @return
         */
        protected JLabel getLabel(String text) {
            JLabel jLabel=new JLabel();
            jLabel.setText(text);
            return jLabel;
        }

        /**��������
         * @param width
         * @param height
         * @return ��������
         */
        protected JTextField getJTextFiled(int width,int height) {
            JTextField jTextField;
            jTextField = new JTextField();
            jTextField.setPreferredSize(new Dimension(width, height));
            return jTextField;
        }

    }


}

