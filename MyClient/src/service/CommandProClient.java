package service;

import java.util.Hashtable;

import main.ChatManager;
import view.ChatWindow;

/**
 * ���������������Կͻ��˷��ص���Ϣ
 * @author dell123
 *���췵�أ�talk;chatKey;��Ϣ
 *�������пͻ��˴������촰�����creatFrame;chatKey;
 */
public class CommandProClient {
	
	public String processTalkMessage(String message,Hashtable<String,ChatWindow>windowTable){
		String[]paras=message.split(";");
		if(paras.length<3){
			return "�����������ԣ������ύ�˿���Ϣ";
		}
		else{
			System.out.println("processTalkMessage message"+message);
			String chatFrameKey=paras[1];//���ڱ�ʶ�����Ϣ�������ĸ����ڵ�
			String info=paras[2];
			ChatWindow window=windowTable.get(chatFrameKey);
			window.appendText("�յ���"+info);
			
		}
		return "success";
	}
	/**
	 * �������촰��
	 * @param message
	 * @return
	 */
	public String processCreatFrame(String message){
		String[]paras=message.split(";");
		if(paras.length<2){
			return "�����������ԣ������ύ�˿���Ϣ";
		}
		else{
			System.out.println("processCreatFram message"+message);
			String chatFrameKey=paras[1];//���ڱ�ʶ�����Ϣ�������ĸ����ڵ�
			ChatWindow frame = new ChatWindow(chatFrameKey);
			frame.setVisible(true);
			//�����������촰�ڣ���ֵ���ͻ��˵Ĵ���table�������Ժ�Դ��ڵ���
			ChatManager.getCM().setWindowTable(chatFrameKey,frame);
			
			
		}
		return "success";
	}
	

}