package main;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.hibernate.type.descriptor.sql.CharTypeDescriptor;

public class ChatManager {

	//实现单例
	private ChatManager(){}
	private static final ChatManager cm = new ChatManager();
	public static ChatManager getChatManager() {
		return cm;
	}
	//这个变量用于记录所有连接到服务器的socket
	Hashtable<String,ChatSocket> socketTable = new Hashtable<String,ChatSocket>();
	//这个变量用于记录所有的会话，value值是一个set用于记录每个聊天的成员socket
	Hashtable<String, Set<ChatSocket>>chartTable=new Hashtable<String, Set<ChatSocket>>();
	public void add(ChatSocket cs) {
		String key=""+cs.getSocket().getPort();
		socketTable.put(key, cs);
		System.out.println(socketTable.keySet().toString());
		
	}
	
	public void remove(ChatSocket cs) {
		String key=""+cs.getSocket().getPort();
		socketTable.remove(key);
		
	}
	
	public void addChart(String key,Set<String>skeys) {
		Set<ChatSocket> chartSet=new HashSet<ChatSocket>() ;
		for(String sk :skeys){
			chartSet.add(socketTable.get(sk));
		}
		if(chartSet!=null){
		chartTable.put(key, chartSet);
		}
		
		
	}
	
	public void removeChart(String key) {
		
		chartTable.remove(key);
		
	}
	
	//实现某一线程的广播
		//chatKey:表明所在的聊天群组key
		public void publish(ChatSocket cs,String out,String chatKey) {
			Set<ChatSocket>chatSet=this.chartTable.get(chatKey);
			System.out.println("length: "+chatSet.size());
			for (ChatSocket csChatSocket:chatSet) {
				System.out.println("chatPort: "+csChatSocket.getSocket().getPort());
				if (!cs.equals(csChatSocket)) {
					csChatSocket.out("talk;"+chatKey+";"+out);
				}
			}
		}

	public Hashtable<String, ChatSocket> getSocketTable() {
		return socketTable;
	}

	public void setSocketTable(Hashtable<String, ChatSocket> socketTable) {
		this.socketTable = socketTable;
	}

	public Hashtable<String, Set<ChatSocket>> getChartTable() {
		return chartTable;
	}

	public void setChartTable(Hashtable<String, Set<ChatSocket>> chartTable) {
		this.chartTable = chartTable;
	}

	
	
	
	
}
