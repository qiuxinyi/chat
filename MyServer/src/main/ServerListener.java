package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {

	//����ִ�к�̨�ķ���
	@Override
	public void run() {
		//1-65535
		try {
			//��ʼ����12345�˿�
			ServerSocket serverSocket = new ServerSocket(12345);
			//ѭ���ļ������Կͻ��˵�����
			while (true) {
				//accept���������main�̣߳�����Ҫ�ŵ���һ���߳��У������ܵ����ӻ᷵��socket
				Socket socket = serverSocket.accept();
				//��������
				JOptionPane.showMessageDialog(null, "�пͻ������ӵ��˱�����12345�˿�");
				//��socket���ݸ��µ��̣߳�����ͨ�ŵ��̣߳�
				ChatSocket cs = new ChatSocket(socket);
				cs.start();
				ChatManager.getChatManager().add(cs);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}