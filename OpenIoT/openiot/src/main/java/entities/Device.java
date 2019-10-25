package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="device")
@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d")
public class Device {

	public static final String FIND_ALL = "Device.findAll";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore @Expose
	private int id;
	
//    @ManyToMany(fetch = FetchType.LAZY) // Using lazy fetching for performance reasons
//    @JoinColumn(name = "user_id")
//    @JsonManagedReference
//	private List<User> users = new ArrayList<>();
	

    @ManyToMany(mappedBy = "devices", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @InvisibleJson
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private User owner;
    
    @NotNull @Expose
	private String name;
    @Expose
	private String imageUrl;
	@NotNull @Expose
	private String data;
	@NotNull @Expose
	private String status; 
	@NotNull @Expose
	private boolean publicDevice;
    
	@OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonBackReference
	@Expose
	private List<Feedback> feedback = new ArrayList<>();

	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void removeUser(User user) {
		this.users.remove(user);
	}
	public int getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPublicDevice() {
		return publicDevice;
	}

	public void setPublicDevice(boolean publicDevice) {
		this.publicDevice = publicDevice;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

	public void addFeedback(Feedback feedback) {
		this.feedback.add(feedback);
		feedback.addDevice(this);
		
	}
}
