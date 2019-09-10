package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entities.Device;
import entities.Feedback;
import entities.User;

@Named(value = "userController")
@RequestScoped
public class UserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Injected DAO EJB:
	@EJB
	private UserDao userDao;
	
	private User user;

	public List<User> getUsers() {
		List<User> reverseTweetList = new ArrayList<User>();
		reverseTweetList.addAll(this.userDao.getAllUsers());
		Collections.reverse(reverseTweetList);
		return reverseTweetList;
	}

	public String saveUser() {
		User tempUser = getTestUser();
		this.userDao.persist(tempUser);
		return "index";
	}

	private User getTestUser() {
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
		tempDevice.setOwnerId(6);
		tempDevice.setPublicDevice(true);
		tempDevice.setStatus("online");
		List<Feedback> feedback = new ArrayList<Feedback>();
		Feedback fb = new Feedback();
		fb.setComment("This device rules!");
		fb.setRating(4);
		feedback.add(fb);
		tempDevice.setFeedback(feedback);
		tempUser.setDevices(devices);
		return tempUser;
	}
	
	public User getUser() {
		if (this.user == null) {
			user = new User();
		}
		return user;
		
	}

}
