package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;

import entities.Device;
import entities.Feedback;
import entities.User;

public class UserTest {

    private static final String PERSISTENCE_UNIT_NAME = "Assignment1";
    private static EntityManagerFactory factory;
	
    @Test
	public void testDatabase() {
		 factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	        EntityManager em = factory.createEntityManager();
			Logger logger = Logger.getLogger(UserTest.class.getName());
			// Persist test user
			User testUser = getTestUser();
			persistUser(testUser, em, logger);
			
	        // read the existing entries and write to console
	        Query q = em.createQuery("select s from USER_TABLE s");
	        @SuppressWarnings("unchecked")
			List<User> users = q.getResultList();
	        for (User s : users) {
	            logger.info("USER "+s.getId()+": " + s.getFname());
	            System.out.println("USER "+s.getId()+": " + s.getFname());
	        }
	        
	        // read the existing entries and write to console
	        Query q2 = em.createQuery("select s from DEVICE s");
	        @SuppressWarnings("unchecked")
			List<Device> devices = q2.getResultList();
	        for (Device d : devices) {
	            logger.info("Device: "+d.getId()+": " + d.getName());
	            System.out.println("Device: "+d.getId()+": " + d.getName());
	        }
	        
	        assertEquals(testUser, users.get(users.size()-1));
	        assertTrue(false);
	        
	        
	}
	
	private static void persistUser(User user, EntityManager em, Logger logger) {
		logger.info("Persisting user...");
		em.persist(user);
	}
	
	private static User getTestUser() {
		User tempUser = new User();
		tempUser.setUname("Jonsor");
		tempUser.setPassword("password");
		tempUser.setFname("Jonas");
		tempUser.setIname("Sørsdal");
		List<Device> devices = new ArrayList<Device>();
		Device tempDevice = new Device();
		tempDevice.setData("data");
		tempDevice.setImageUrl("imageUrl");
		tempDevice.setName("name");
//		tempDevice.setOwnerId(6);
		tempDevice.setPublicDevice(true);
		tempDevice.setStatus("online");
		List<Feedback> feedback = new ArrayList<Feedback>();
		Feedback fb = new Feedback();
		fb.setComment("This device rules!");
		fb.setRating(4);
		feedback.add(fb);
//		tempDevice.setFeedback(feedback);
		tempUser.setDevices(devices);
		return tempUser;
	}

}
