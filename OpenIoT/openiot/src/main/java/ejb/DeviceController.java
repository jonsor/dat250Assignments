package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import entities.Device;
import entities.Feedback;
import entities.User;

@Named(value = "deviceController")
@RequestScoped
public class DeviceController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Injected DAO EJB:
	@EJB
	private DeviceDao deviceDao;
	
	private Device device;

	private Device getTestDevice() {
		Device tempDevice = new Device();
		tempDevice.setData("Data");
		tempDevice.setImageUrl("imageurl");
		tempDevice.setName("name");
		tempDevice.setPublicDevice(true);
		tempDevice.setStatus("1");
		return tempDevice;
	}
	
	public String saveDevice() {
		Device d = this.getTestDevice();
		this.deviceDao.persist(d);
		return "index";
	}

	public List<Device> getDevices() {
		List<Device> deviceList = new ArrayList<Device>();
		deviceList.addAll(this.deviceDao.getAllDevices());
		Collections.reverse(deviceList);
		return deviceList;
	}
}
