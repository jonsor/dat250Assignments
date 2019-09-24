package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_table")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User {

	public static final String FIND_ALL = "User.findAll";
	
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private int id;
    
    @NotNull
	private String uname;
    @NotNull
	private String password;
	private String fname;
	private String lname;
	
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonBackReference
	private List<Device> devices = new ArrayList<>();

    public User() {}
    
    public User(int id, String uname, String password, String fname, String lname, List<Device> devices) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.devices = devices;
	}

	public void addDevice(Device device) {
        devices.add(device);
        device.setUser(this);
    }
 
    public void removedDevice(Device device) {
    	devices.remove(device);
    	device.setUser(null);
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
