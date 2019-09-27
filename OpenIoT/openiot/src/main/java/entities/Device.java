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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="device")
@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d")
public class Device {

	public static final String FIND_ALL = "Device.findAll";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private int id;
	
//    @ManyToMany(fetch = FetchType.LAZY) // Using lazy fetching for performance reasons
//    @JoinColumn(name = "user_id")
//    @JsonManagedReference
//	private List<User> users = new ArrayList<>();
	

    @ManyToMany(mappedBy = "devices", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
    
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    @JsonManagedReference
//    private User owner;
    
    @NotNull
	private String name;
	private String imageUrl;
	@NotNull
	private String data;
	@NotNull
	private String status; 
	@NotNull
	private boolean publicDevice;
    
	@OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonBackReference
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
	public int getId() {
		return id;
	}

//	public int getOwnerId() {
//		return ownerId;
//	}

//	public void setOwnerId(int ownerId) {
//		this.ownerId = ownerId;
//	}

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
}
