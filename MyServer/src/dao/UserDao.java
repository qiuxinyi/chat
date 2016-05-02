package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import util.DatabaseUtil;

public class UserDao extends DatabaseUtil<User>{
	
	public User getUserByUserName(String userName){
		Session session=null;
		Transaction tx=null;
		session=init();
		tx=session.beginTransaction();
		Query query = session.getNamedQuery("getObjectOfUserByUserName");
		query.setParameter("userName", userName);
		
		try{
			List list = query.list();
			System.out.println(list.size());
			if(list.size()==0){
				tx.commit();
				return null;
			}
				
			else
			{
				User user= (User) list.get(0);
				tx.commit();
				return user;
				
			}
			
			
		}catch(Exception ce){
			tx.rollback();
		}finally{
			session.close();
		}
		return null;
	}

}
