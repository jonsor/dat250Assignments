package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PhoneNumber {
	
   @Id
   @GeneratedValue( strategy= GenerationType.AUTO ) 	
   private int id;
   
	private String prefix;
	private String number;
}
