package service;

import java.util.Hashtable;

import main.ChatManager;
import view.ChatWindow;

/**
 * 用来处理服务器对客户端返回的消息
 * @author dell123
 *聊天返回：talk;chatKey;消息
 *服务器叫客户端创建聊天窗口命令：creatFrame;chatKey;
 */
public class CommandProClient {
	
	public String processTalkMessage(String message,Hashtable<String,ChatWindow>windowTable){
		String[]paras=message.split(";");
		if(paras.length<3){
			return "参数个数不对，或是提交了空消息";
		}
		else{
			System.out.println("processTalkMessage message"+message);
			String chatFrameKey=paras[1];//用于标识这个信息是属于哪个窗口的
			String info=paras[2];
			ChatWindow window=windowTable.get(chatFrameKey);
			window.appendText("收到："+info);
			
		}
		return "success";
	}
	/**
	 * 创建聊天窗口
	 * @param message
	 * @return
	 */
	public String processCreatFrame(String message){
		String[]paras=message.split(";");
		if(paras.length<2){
			return "参数个数不对，或是提交了空消息";
		}
		else{
			System.out.println("processCreatFram message"+message);
			String chatFrameKey=paras[1];//用于标识这个信息是属于哪个窗口的
			ChatWindow frame = new ChatWindow(chatFrameKey);
			frame.setVisible(true);
			//将创建的聊天窗口，赋值给客户端的窗口table，方便以后对窗口调用
			ChatManager.getCM().setWindowTable(chatFrameKey,frame);
			
			
		}
		return "success";
	}
	

}
