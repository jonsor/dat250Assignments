package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

import entities.Device;
import entities.User;
import io.swagger.annotations.Api;

@Path("/devices")
@Api(value = "Device")
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
		
	    Gson gson = new Gson();
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
	
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(device);
	    
		return Response.ok(jsonString).build();
	}
	
	@GET
	@Path("{id}/registrations")
	public Response getDeviceRegistrations(@PathParam("id") String id) {
		int idInt = Integer.parseInt(id);
	    TypedQuery<String> query = em.createQuery(
	            "SELECT u.uname FROM User u INNER JOIN Device d ON u.id = d.user_id WHERE d.id = ?1", String.class);
	    List<String> userNames = new ArrayList<String>(query.setParameter(1, idInt).getResultList());
	    Gson gson = new Gson();
	    String jsonString = gson.toJson(userNames);
	    
		return Response.ok(jsonString).build();
	}
}
