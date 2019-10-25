package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="FEEDBACK_TABLE")
public class Feedback {

	@Id
	@GeneratedValue( strategy= GenerationType.AUTO ) 
	@JsonIgnore
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	@JsonManagedReference
	private Device device;

	@Expose
	private String comment;

	@Min(0)
	@Max(5)
	@Expose
	private int rating;

	public Feedback() {}
	public Feedback(String comment, int rating) {
		this.comment = comment;
		this.rating = rating;
	}

	public void addDevice(Device device) {
		this.device = device;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
