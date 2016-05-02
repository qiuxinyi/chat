package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseUtil<T> {
	
	
	public Session init() {
		Configuration cfg=new Configuration().configure();
		//SessionFactory factory=cfg.buildSessionFactory();
		
		StandardServiceRegistryBuilder ssrb=new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		
		ServiceRegistry service=ssrb.build();
		SessionFactory factory=cfg.buildSessionFactory(service);
		
		Session session=factory.openSession();
		return session;
		
	}


	public void add(T t){
		Session session=null;
		Transaction tx=null;
		session=init();
		tx=session.beginTransaction();
		System.out.println(session==null);
		try{
			
			session.save(t);
			tx.commit();
		}catch(Exception ce){
			tx.rollback();
		}finally{
			session.close();
		}
		
	}

	
	public Object getObjectById(Class<T> t,int id){
		Object o = null;
		Session session=null;
		Transaction tx=null;
		session=init();
		tx=session.beginTransaction();
		try{
			o=session.get(t,id);
			tx.commit();
			
		}catch(Exception ce){
			tx.rollback();
		}finally{
			session.close();
		}
		return o;
	
	}
}
