package ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Device;
import entities.Feedback;
import entities.User;

@Stateless
public class UserDao {
    // Injected database connection:
	@PersistenceContext(unitName="Assignment1")
    private EntityManager em;

    // Stores a new user:
    public void persist(User user) {
        em.persist(user);
        Logger logger = Logger.getLogger(UserDao.class.getName());
        logger.info(user.getFname());
        logger.info(Integer.toString(user.getDevices().size()));
        for(Device d : user.getDevices()) {
        	logger.info(d.getName());
        	em.persist(d);
        	for(Feedback f : d.getFeedback()) {
        		logger.info(f.getComment());
        		em.persist(f);
        	}
        }
    }

    // Retrieves all the users:
    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

        Query query = em.createQuery("SELECT u FROM User u");
        List<User> users = new ArrayList<User>();
        users = query.getResultList();
        return users;
    }
    
    @SuppressWarnings("unchecked")
	public List<Device> getUserDevices(int userId) {

        Query query = em.createQuery("SELECT u FROM Devices u where user_id="+ userId);
        List<Device> users = new ArrayList<Device>();
        users = query.getResultList();
        return users;
    }
}
