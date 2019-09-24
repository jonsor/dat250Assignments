package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/users")
@Tag(name = "User", description = "Get and create users")
@Stateless
public class UserController {

	@PersistenceContext(unitName = "Assignment1")
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
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
	public Response get(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
		User user = em.find(User.class, idInt);
		if (user == null)
			throw new NotFoundException();
	
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(user);
	    
		return Response.ok(jsonString).build();
	}
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
		if (user == null) 
			throw new NotFoundException();
		
    	try {
    		em.persist(user);    		
    	} catch(EntityExistsException e) {
    		return Response.status(Status.CONFLICT).build();
    	} catch(IllegalArgumentException e) {
    		return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
    	}
    	
        return Response.status(Status.CREATED).build();
    }
}
