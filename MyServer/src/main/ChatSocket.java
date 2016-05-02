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
			System.out.println("�Ͽ���һ���ͻ�������");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("����ip��ַΪ"+socket.getInetAddress()
				+"��������Ķ˿ں�Ϊ "+socket.getPort());
		out("���Ѿ����ӵ�����������");
		try {
			System.out.println("chatSocketPort: "+socket.getInetAddress());
			//���ϵض�ȡ�ͻ��˷��������ݣ����������������ͻ���
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
			System.out.println("�Ͽ���һ���ͻ�������");
			ChatManager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("�Ͽ���һ���ͻ�������");
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