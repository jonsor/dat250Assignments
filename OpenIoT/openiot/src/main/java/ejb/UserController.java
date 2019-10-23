package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entities.Device;
import entities.User;

@Named(value = "userController")
@RequestScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Injected DAO EJB:
	@EJB
	private UserDao userDao;
	
	private User user;

	public List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		userList.addAll(this.userDao.getAllUsers());
		Collections.reverse(userList);
		return userList;
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
		tempUser.setLname("Sørsdal");
		Device tempDevice = new Device();
		tempDevice.setData("data");
		tempDevice.setImageUrl("imageUrl");
		tempDevice.setName("name");
//		tempDevice.setOwnerId(6);
		tempDevice.setPublicDevice(true);
		tempDevice.setStatus("online");
		tempUser.addDevice(tempDevice);
		return tempUser;
	}
	
	public User getUser() {
		if (this.user == null) {
			user = new User();
		}
		return user;
		
	}

}
