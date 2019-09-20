package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="device")
@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d")
public class Device {

	public static final String FIND_ALL = "Device.findAll";
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
    @ManyToOne(fetch = FetchType.LAZY) // Using lazy fetching for performance reasons
    @JoinColumn(name = "user_id")
    private User user;
    
	//@OneToOne(targetEntity=User.class)
	//private int ownerId;
	
	private String name;
	private String imageUrl;
	private String data;
	private String status; 
	private boolean publicDevice;
	
//    @OneToMany(mappedBy = "device", targetEntity=Feedback.class, cascade = CascadeType.ALL)
//	private List<Feedback> feedback;

    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

//	public int getOwnerId() {
//		return ownerId;
//	}
//
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

//	public List<Feedback> getFeedback() {
//		return feedback;
//	}
//
//	public void setFeedback(List<Feedback> feedback) {
//		this.feedback = feedback;
//	}

    
}
