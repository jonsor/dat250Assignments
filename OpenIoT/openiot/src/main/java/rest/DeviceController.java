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

import entities.Device;
import entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import misc.ApiHelper;

@Path("/devices")
@Tag(name = "Device", description = "Get and create devices")
@Stateless
public class DeviceController {

	@PersistenceContext(unitName = "Assignment1")
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDevices() {
		TypedQuery<Device> query = em.createNamedQuery(Device.FIND_ALL, Device.class);
		if (query == null)
			throw new NotFoundException();
		List<Device> devices = new ArrayList<Device>(query.getResultList());
		
	    Gson gson = ApiHelper.getGson();
	    String jsonString = gson.toJson(devices);
	    
		return Response.ok(jsonString).build();
	}

	@GET
	@Path("{id}")
	public Response getDevice(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
		Device device = em.find(Device.class, idInt);
		if (device == null)
			throw new NotFoundException();
	
	    Gson gson = ApiHelper.getGson();
	    String jsonString = gson.toJson(device);
	    
		return Response.ok(jsonString).build();
	}
	
	@GET
	@Path("{id}/registrations")
	public Response getDeviceRegistrations(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
	    TypedQuery<String> query = em.createQuery(
	            "SELECT u.uname FROM User u INNER JOIN Device d WHERE d.id = ?1", String.class);
	    List<String> userNames = new ArrayList<String>(query.setParameter(1, idInt).getResultList());
	    Gson gson = ApiHelper.getGson();
	    String jsonString = gson.toJson(userNames);
	    
		return Response.ok(jsonString).build();
	}
	
	@GET
	@Path("{id}/registrations/{rid}")
	public Response getDeviceRegistrations(@PathParam("id") String id, @PathParam("rid") String rid) {
		int idInt = Integer.parseInt(id);
		int ridInt = Integer.parseInt(rid);
	    TypedQuery<String> query = em.createQuery(
	            "SELECT u.uname FROM User u INNER JOIN Device d WHERE d.id = ?1 and d.user_id = ?2", String.class);
	    List<String> userNames = new ArrayList<String>(query.setParameter(1, idInt).setParameter(2, ridInt).getResultList());
	    Gson gson = ApiHelper.getGson();
	    String jsonString = gson.toJson(userNames);
	    
		return Response.ok(jsonString).build();
	}
	
    @POST
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("id") String id, Device device) {
		if (device == null) 
			throw new NotFoundException();
		
		int idInt = Integer.parseInt(id);
		User user = em.find(User.class, idInt);
		if (user == null)
			throw new NotFoundException();
		user.addDevice(device);
		
    	try {
    		em.persist(device);
    		em.merge(user);    		
    	} catch(EntityExistsException e) {
    		return Response.status(Status.CONFLICT).build();
    	} catch(IllegalArgumentException e) {
    		return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
    	}
    	
        return Response.status(Status.CREATED).build();
    }
	
}
