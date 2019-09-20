package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Device;
import entities.Feedback;
import entities.User;

@Stateless
public class DeviceDao {
    // Injected database connection:
	@PersistenceContext(unitName="Assignment1")
    private EntityManager em;

    // Stores a new user:
    public void persist(Device device) {
        em.persist(device);
    }

    // Retrieves all the devices:
    @SuppressWarnings("unchecked")
	public List<Device> getAllDevices() {

        Query query = em.createQuery("SELECT d FROM Device d");
        List<Device> devices = new ArrayList<Device>();
        devices = query.getResultList();
        return devices;
    }
    
    // Retrieves devices from a specific user
    @SuppressWarnings("unchecked")
	public List<Device> getUserDevices(int userId) {
        Query query = em.createQuery("SELECT u FROM Device u where user_id="+ userId);
        List<Device> users = new ArrayList<Device>();
        users = query.getResultList();
        return users;
    }
    

    public void registerDeviceToUser(Device device, User user) {
    	Query query = em.createQuery("INSERT INTO Device VALUES (id, data, imageurl, name, publicdevice, status, user_id)");
    	query.setParameter("id", device.getId());
    	query.setParameter("data", device.getData());
    	query.setParameter("imageurl", device.getImageUrl());
    	query.setParameter("name", device.getName());
    	query.setParameter("oublicdevice", device.getStatus());
    	query.setParameter("status", device.getStatus());
    	query.setParameter("user_id", user.getId());
    	query.executeUpdate();
    }
    
}
