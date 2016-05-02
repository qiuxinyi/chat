package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

import service.CommandProClient;
import view.ChatWindow;
import view.MainWIndow;


public class ChatManager {

	private ChatManager(){}
	private CommandProClient process=new CommandProClient();
	private static final ChatManager instance = new ChatManager();
	public static ChatManager getCM() {
		return instance;
	}
	//用来保存该客户端创建的所有聊天窗口
	Hashtable<String,ChatWindow> windowTable=new Hashtable<String,ChatWindow>() ;
	String IP;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public void setWindowTable(String chatKey,ChatWindow window) {
		this.windowTable.put(chatKey, window);
		System.out.println(this.windowTable.keySet().toString());
		window.appendText("文本框已与ChatManager绑定完成了");
	}
	
	public void connect(String ip) {
		this.IP = ip;
		new Thread(){

			@Override
			public void run() {
				try {
					int count=0;
					socket = new Socket(IP, 12345);
					
					writer = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),"UTF-8"));
					
					reader = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream(),"UTF-8"));
					String line;
					//处理服务器发来的信息/命令
					while ((line = reader.readLine()) != null) {
						if(line.startsWith("talk")){
						process.processTalkMessage(line,windowTable);
						}
						else if(line.startsWith("creatFrame")){
							process.processCreatFrame(line);
						}
						
						
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	//向服务器发送聊天信息
	public void send(String out,String chatKey) {
		//获得当前的活动窗口
		ChatWindow window=windowTable.get(chatKey);
		if (writer != null) {
			writer.write(out+"\n");
			writer.flush();
		}else {
			window.appendText("连接失败");
		}
	}
	
	//向服务器发送命令
	public void sendCommand(String out) {
		if (writer != null) {
			writer.write(out+"\n");
			writer.flush();
		}else {
			
		}
	}
}
