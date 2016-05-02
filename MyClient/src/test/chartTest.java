package test;
import java.util.*;

import org.junit.Test;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
public class chartTest {
	@Test
	public void randomTest(){
		Random r=new Random();
		int port=r.nextInt(1000)+1023;
		System.out.println(port);
	}

}
