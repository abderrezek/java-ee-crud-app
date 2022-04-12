package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConfig {
	
	private static SessionFactory sessionFactory;
	private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure()
			.build();
	
	static {
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			System.err.println(ex.getLocalizedMessage());
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public static SessionFactory getFactory() {
		return sessionFactory;
	}
	
	public static Session openSession() {
		return getFactory().openSession();
	}

}
