package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import service.CommandProService;

public class ChatSocket extends Thread {
	
	Socket socket;
	
	public ChatSocket(Socket s){
		this.socket = s;
	}
	
	public void out(String out) {
		try {
			socket.getOutputStream().write((out+"\n").getBytes("UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("您的ip地址为"+socket.getInetAddress()
				+"分配给您的端口号为 "+socket.getPort());
		out("你已经连接到本服务器了");
		try {
			System.out.println("chatSocketPort: "+socket.getInetAddress());
			//不断地读取客户端发来的数据，并将它发给其他客户端
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(),"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				CommandProService server=new CommandProService();
				if(line.startsWith("command")&&line.contains("friendTalk")){
					server.friendTalk(line);
				}
				else if(line.startsWith("command")&&line.contains("groupTalk")){
					server.groupTalk(line);
				}
				else if(line.startsWith("talk")){
					server.processTalkMessage(line, this);
				}
				//ChatManager.getChatManager().publish(this, line);
			}
			br.close();
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
		
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
}
