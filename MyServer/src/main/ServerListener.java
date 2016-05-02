package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {

	//用来执行后台的方法
	@Override
	public void run() {
		//1-65535
		try {
			//开始监听12345端口
			ServerSocket serverSocket = new ServerSocket(12345);
			//循环的监听来自客户端的连接
			while (true) {
				//accept方法会阻断main线程，所以要放到另一个线程中，当接受到连接会返回socket
				Socket socket = serverSocket.accept();
				//建立连接
				JOptionPane.showMessageDialog(null, "有客户端链接到了本机的12345端口");
				//将socket传递给新的线程（进行通信的线程）
				ChatSocket cs = new ChatSocket(socket);
				cs.start();
				ChatManager.getChatManager().add(cs);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
