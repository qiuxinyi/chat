package service;

import java.util.*;

import main.ChatManager;
import main.ChatSocket;
import model.User;
import dao.*;

//用于处理客户端发来的命令
/**
 * command 的格式为 command;动作;参数
 * 如果是请求私聊则为：command;friendTalk;自己的用户id;对方的用户id;
 * 如果是请求群聊则为：command;groupTalk;自己的用户id;对方1的用户id;对方2的用户id;
 * 发送聊天信息：talk;chatKey;消息
 * 创建聊天窗口"creatFrame;key;"命令客户端创建一个以key为可以的聊天窗口
 * @author dell123
 *
 */
public class CommandProService {
	//该方法用于处理私聊的请求
	public String friendTalk(String command){
		ArrayList<Integer>ids=new ArrayList<Integer>();
		String[]paras=command.split(";");
		if(paras.length!=4){
			return "friendTalk the number of parameter is wrong";
		}
		else {
			int userId=Integer.parseInt(paras[2]);
			int friId=Integer.parseInt(paras[3]);
			ids.add(userId);
			ids.add(friId);
			processTalk(ids);
		}
		return"success";
	}
	//该方法用于处理群聊的请求
	public String groupTalk(String command){
		ArrayList<Integer>ids=new ArrayList<Integer>();
		String[]paras=command.split(";");
		if(paras.length<4){
			return "groupTalk the number of parameter is wrong";
		}
		else {
			for(int i=2;i<paras.length;i++){
				ids.add(Integer.parseInt(paras[i]));
			
			}
		
			processTalk(ids);
		}
		return"success";
	}
	//对聊天请求的一个方法抽象
	public void processTalk(ArrayList<Integer>ids){
		UserDao userDao=new UserDao();
		Set<ChatSocket>chartSocketSet=new HashSet<ChatSocket>();
		ArrayList<Integer> portList=new ArrayList<Integer>();
		for(int id:ids){
			User user=(User) userDao.getObjectById(User.class, id);
			portList.add(user.getPort());
			
		}
		Collections.sort(portList);
		String key="";
		
		for(int port :portList){
			System.out.println("socketTablesize: "+ChatManager.getChatManager().getSocketTable().size());
			ChatSocket socket=ChatManager.getChatManager().getSocketTable().get(port+"");
			chartSocketSet.add(socket);
			key=key+port+":";
		}
		System.out.println("key: "+key);
		System.out.println("chartSetLength "+chartSocketSet.size());
		ChatManager.getChatManager().getChartTable().put(key, chartSocketSet);
		for(ChatSocket socket :chartSocketSet){
			socket.out("creatFrame;"+key+";");//向客户端发送命令，叫它创建聊天窗口
		}
	}
	
	//对客户端要求进行的聊天数据进行发送，从而完成私聊或者群聊信息的传递
	public String processTalkMessage(String message,ChatSocket cs){
		String[]paras=message.split(";");
		if(paras.length<3){
			return "参数个数不对，或是提交了空消息";
		}
		else{
			String chatKey=paras[1];//用于标识这个信息是属于哪个窗口的
			String info=paras[2];
			//对属于这个群组里面的socket进行广播
			ChatManager.getChatManager().publish(cs, info, chatKey);
		}
		return "success";
	}


}
