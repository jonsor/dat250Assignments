package test;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.User;

public class UserTest {

    private static final String PERSISTENCE_UNIT_NAME = "Assignment1";
    private static EntityManagerFactory factory;
	
	public static void main(String[] args) {
		 factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	        EntityManager em = factory.createEntityManager();
			Logger logger = Logger.getLogger("UserTest");
	        // read the existing entries and write to console
	        Query q = em.createQuery("select s from USER_TABLE s");
	        @SuppressWarnings("unchecked")
			List<User> users = q.getResultList();
	        for (User s : users) {
	            logger.info("USER "+s.getId()+": " + s.getFname());
	            System.out.println("USER "+s.getId()+": " + s.getFname());
	        }
	}

}
