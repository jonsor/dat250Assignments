package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PhoneNumber {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String prefix;
	private String number;
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
}
