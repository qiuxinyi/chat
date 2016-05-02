package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * @author slw
 *
 */
@Entity
public class GroupTalk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1530127413381718586L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int isConnect;//判断这个群是否连接了
	private int port;//这个群服务器的端口号，ip为服务器的ip
	
	@ElementCollection
	@CollectionTable(name="memberList",joinColumns=@JoinColumn(name="id"))
	@Column(name="memberList")
	protected List<User> memberList = new ArrayList<User>();

	
	public GroupTalk(){
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIsConnect() {
		return isConnect;
	}


	public void setIsConnect(int isConnect) {
		this.isConnect = isConnect;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public List<User> getMemberList() {
		return memberList;
	}


	public void setMemberList(List<User> memberList) {
		this.memberList = memberList;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
