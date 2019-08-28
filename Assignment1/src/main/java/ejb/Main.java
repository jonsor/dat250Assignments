package ejb;

import javax.ejb.EJB;

public class Main {

	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		userDao.getAllUsers();

	}

}
