package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "user_table")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User {

	public static final String FIND_ALL = "User.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private int id;

	@NotNull @Expose
	private String uname;
	@NotNull @Expose
	private String password;
	@Expose
	private String fname;
	@Expose
	private String lname;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_devices",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "device_id")}
    )
	@InvisibleJson
	@Expose
	private Set<Device> devices = new HashSet<>();

	public User() {
	}

	public User(int id, String uname, String password, String fname, String lname, Set<Device> devices) {
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
		device.addUser(this);
	}

	public void removedDevice(Device device) {
		devices.remove(device);
		device.addUser(this);
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

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
}
