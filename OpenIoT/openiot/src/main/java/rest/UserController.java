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

import com.google.gson.Gson;

import entities.User;
import io.swagger.annotations.Api;

@Path("/users")
@Api(value = "User")
@Stateless
public class UserController {

	@PersistenceContext(unitName = "Assignment1")
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
		if (query == null)
			throw new NotFoundException();
		List<User> users = new ArrayList<User>(query.getResultList());
		
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(users);
	    
		return Response.ok(jsonString).build();
	}

	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
		User user = em.find(User.class, idInt);
		if (user == null)
			throw new NotFoundException();
	
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(user);
	    
		return Response.ok(jsonString).build();
	}
}
