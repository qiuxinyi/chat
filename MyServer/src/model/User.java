package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
     name = "getObjectOfUserByUserName",
     query = "from User where userName =:userName ")
     
   
})
@Entity
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1530127413381718585L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String userName;
	private String secrect;
	private int oneLine=0;//如果上线则为1，否则为0
	private String ip;
	private int port;//与服务器连接的socket的port
	
	@ElementCollection
	@CollectionTable(name="friendList",joinColumns=@JoinColumn(name="id"))
	@Column(name="friendList")
	protected List<User> friendList = new ArrayList<User>();
	
	@ElementCollection
	@CollectionTable(name="groupList",joinColumns=@JoinColumn(name="id"))
	@Column(name="groupList")
	protected List<GroupTalk> groupList = new ArrayList<GroupTalk>();
	
	public User(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecrect() {
		return secrect;
	}

	public void setSecrect(String secrect) {
		this.secrect = secrect;
	}

	public int getOneLine() {
		return oneLine;
	}

	public void setOneLine(int oneLine) {
		this.oneLine = oneLine;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<User> friendList) {
		this.friendList = friendList;
	}

	public List<GroupTalk> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupTalk> groupList) {
		this.groupList = groupList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

	
}
