package com.example.loginreg.services;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.loginreg.models.User;
import com.example.loginreg.repositories.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder bcrypt;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
		this.bcrypt = new BCryptPasswordEncoder();
	}
	
	public boolean isMatch(String password, String dbpass) {
		if(bcrypt.matches(password, dbpass)) {
			return true;
		}else {
			return false;
		}
	}
	public void login(HttpSession s,long id){s.setAttribute("id",id);}
	
	public void logout(HttpSession s){s.setAttribute("id",null);}
	
	public String redirect(){return "redirect:/users/new";}
	
	public boolean isValid(HttpSession session){
		if(session.getAttribute("id") == null){return false;}
		else{return true;}
	}

	
	public void create(User user) {
		user.setPassword( bcrypt.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void update(User user) {
		create(user);
	}
	
	public ArrayList<User>all(){
		return(ArrayList<User>)userRepository.findAll();
		
	}
	
	public User findById(long id) {
		return(User) userRepository.findOne(id);
	}
	
	public void destroy(User user) {
		userRepository.delete(user);
	}
	
	public User findByEmail(String email) {
		return (User) userRepository.findByEmail(email);
	}
	
}
