package service;

import java.util.*;

import main.ChatManager;
import main.ChatSocket;
import model.User;
import dao.*;

//���ڴ����ͻ��˷���������
/**
 * command �ĸ�ʽΪ command;����;����
 * ���������˽����Ϊ��command;friendTalk;�Լ����û�id;�Է����û�id;
 * ���������Ⱥ����Ϊ��command;groupTalk;�Լ����û�id;�Է�1���û�id;�Է�2���û�id;
 * ����������Ϣ��talk;chatKey;��Ϣ
 * �������촰��"creatFrame;key;"����ͻ��˴���һ����keyΪ���Ե����촰��
 * @author dell123
 *
 */
public class CommandProService {
	//�÷������ڴ���˽�ĵ�����
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
	//�÷������ڴ���Ⱥ�ĵ�����
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
	//�����������һ����������
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
			socket.out("creatFrame;"+key+";");//��ͻ��˷�����������������촰��
		}
	}
	
	//�Կͻ���Ҫ����е��������ݽ��з��ͣ��Ӷ����˽�Ļ���Ⱥ����Ϣ�Ĵ���
	public String processTalkMessage(String message,ChatSocket cs){
		String[]paras=message.split(";");
		if(paras.length<3){
			return "�����������ԣ������ύ�˿���Ϣ";
		}
		else{
			String chatKey=paras[1];//���ڱ�ʶ�����Ϣ�������ĸ����ڵ�
			String info=paras[2];
			//���������Ⱥ�������socket���й㲥
			ChatManager.getChatManager().publish(cs, info, chatKey);
		}
		return "success";
	}


}