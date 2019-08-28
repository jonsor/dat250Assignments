package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;

@Stateless
public class UserDao {
    // Injected database connection:
	@PersistenceContext(unitName="Assignment1")
    private EntityManager em;

    // Stores a new user:
    public void persist(User user) {
        em.persist(user);
    }

    // Retrieves all the users:
    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

        Query query = em.createQuery("SELECT u FROM User u");
        List<User> users = new ArrayList<User>();
        users = query.getResultList();
        return users;
    }
}
