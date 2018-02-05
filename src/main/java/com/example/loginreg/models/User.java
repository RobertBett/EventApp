package com.example.loginreg.models;





import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User{
	@Id
	@GeneratedValue
	private long id;

	@Size(min=8)
	private String email;
	
	@Size(min=1,max=255,message ="Name Cannot Be Blank")
	private String firstName;

	@Size(min=1,max=255)
	private String lastName;

	@Size(min=8,max=255)
	private String password;

	private boolean admin;

	@Transient
	@Size(min=8,max=255)	
	private String confirm;

	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date updatedAt;
	
	@Size(min=1,max=255)
	private String city;
	
	@Size(min=2, max=2)
	private String state;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="users_events",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="event_id")
			
	)
	private List<Event>events;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Message>messages;
	
	@PrePersist
	public void onCreate(){this.createdAt = new Date();}
	@PreUpdate
	public void onUpdate(){this.updatedAt = new Date();}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public void setConfirm(String confirm){
		this.confirm=confirm;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public void setAdmin(boolean admin){this.admin=admin;}
	
	public String getEmail(){return email;}
	
	public String getFirstName(){return firstName;}
	
	public String getLastName(){return lastName;}
	
	public String getPassword(){return password;}
	
	public String getConfirm(){return confirm;}
	
	public boolean isAdmin(){return admin;}

	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public User(){
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
}