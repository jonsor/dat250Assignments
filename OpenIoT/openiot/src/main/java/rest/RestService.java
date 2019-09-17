package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Device;
import entities.User;

@Path("/users")
@Stateless
public class RestService {

	@PersistenceContext(unitName = "Assignment1")
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
		List<User> users = new ArrayList<User>(query.getResultList());
		return Response.ok(users).build();
	}

	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
		User user = em.find(User.class, idInt);
		
		if (user == null)
			throw new NotFoundException();
		
		//List<Device> devices = new ArrayList<Device>();
		TypedQuery<Device> query = em.createNamedQuery(Device.FIND_ALL, Device.class);
		List<Device> devices = new ArrayList<Device>(query.getResultList());
//		Device device = (Device) em.createQuery("SELECT d FROM Device d where d.USER_ID='"+ idInt + "'");
//		devices.add(device);
		user.setDevices(devices);
		return Response.ok(user).build();
	}
}
