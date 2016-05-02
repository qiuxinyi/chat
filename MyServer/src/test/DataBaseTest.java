package test;
import java.util.*;

import model.*;

import org.junit.Test;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import dao.UserDao;
public class DataBaseTest {
	
	@Test
	public void createTable(){
		Configuration cfg=new Configuration().configure();
		SchemaExport se=new SchemaExport(cfg);
		se.create(true, true);
		
	}
	
	@Test
	public void addUser(){
		UserDao userDao=new UserDao();
		
		User user1=new User();
		user1.setUserName("test1");
		
		userDao.add(user1);
		
		
		User user2=new User();
		user2.setUserName("test2");
		userDao.add(user2);
		
		List<User> friendList=new ArrayList<User>();
		friendList.add(user2);
		friendList.add(user1);
		User user=new User();
		user.setIp("127.0.0.1");
		user.setOneLine(0);
		user.setUserName("test");
		user.setFriendList(friendList);
		userDao.add(user);
		
	}
	
	@Test
	public void getUserById(){
		UserDao userDao=new UserDao();
		User user=(User)userDao.getObjectById(User.class, 4);
		System.out.println(user.getUserName());
	}

	@Test
	public void getUserByUserName(){
		UserDao userDao=new UserDao();
		User user=(User)userDao.getUserByUserName("test");
		System.out.println(user.getUserName()+" "+user.getId());
	}
}
