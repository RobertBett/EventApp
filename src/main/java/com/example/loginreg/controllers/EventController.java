package com.example.loginreg.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.loginreg.models.Event;
import com.example.loginreg.models.Message;
import com.example.loginreg.models.User;
import com.example.loginreg.services.EventService;
import com.example.loginreg.services.MessageService;
import com.example.loginreg.services.UserService;



@Controller
@RequestMapping("/events")
public class EventController {
	private EventService eventService;
	private MessageService messageService;
	private UserService userService;

	public EventController(EventService eventService, MessageService messageService,UserService userService) {
		this.eventService = eventService;
		this.messageService= messageService;
		this.userService = userService;
	}
	
	@RequestMapping("")
	public String events(@ModelAttribute("event") Event event,HttpSession s, Model model) {
		if( s.getAttribute("id") != null ){
			long user_id =(long)s.getAttribute("id");
			User user = userService.findById(user_id);
			ArrayList<Event> userStates = eventService.findByState(user.getState());
			System.out.println("hello");
//			ArrayList<Event> notUserStates = eventService.findNotByState(user.getState());
			
			model.addAttribute("userStates", userStates);
//			model.addAttribute("notUserStates", notUserStates);
			
			ArrayList<Event> allEvents =(ArrayList<Event>)eventService.all();
			ArrayList<Event> notUserStates = new ArrayList<Event>();
			ArrayList<Event> joinedStates= new ArrayList<Event>();
			ArrayList<Event> allUserStates = new ArrayList<Event>();
			ArrayList<Event> notJoinedStates= new ArrayList<Event>();
			
			
			//this finds states that are equal to the user states
			for(int i=0; i<allEvents.size(); i++ ) {
				 if(allEvents.get(i).getState().equals(user.getState()))
					 allUserStates.add(allEvents.get(i));
			//this loops through all the user States 
				 for(int x=0; x<allUserStates.size(); x++ ) {
					 
			//this loops through all the users in the user states
					 for(int e=0;e<allUserStates.get(x).getUsers().size();e++) {
			//this checks if the user has joined an event by comparing user IDs
						 if(allUserStates.get(x).getUsers().get(e).equals(user)) 
							 if(!joinedStates.contains(allUserStates.get(x))) {
								 joinedStates.add(allUserStates.get(x));}
			//this does the opposite of ^^
//						 if( !allUserStates.get(x).getUsers().get(e)(user))
//							 notJoinedStates.add(allUserStates.get(x));
				 }
				}
				 if( !allEvents.get(i).getState().equals(user.getState()))
					 notUserStates.add(allEvents.get(i));

				
			}
			System.out.println(notJoinedStates);
			System.out.println(joinedStates);
			model.addAttribute("notJoinedEvents", notJoinedStates);
			model.addAttribute("joinedEvents", joinedStates);
			model.addAttribute("notUserStates", notUserStates);
			return "dashboard";
		}else{

			return "redirect:/";
		}
	}
	
	@PostMapping("/new")
	public String create(@Valid @ModelAttribute("event") Event event, BindingResult res,@RequestParam("file") MultipartFile file) {
		System.out.println("hellodscscdfcfd##");
		if(res.hasErrors()|| file.isEmpty()) {
			System.out.println("hellodscs");
			return "redirect:/events";}
		else {
			try {	byte[] bytes = file.getBytes();
				
				// Creating the directory to store file
				File dir = new File("src/main/resources/static/images");
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				event.setPicture(file.getOriginalFilename());
				eventService.create(event);
			} catch (Exception e) {
				return "redirect:/events";
		}return "redirect:/events";
	}


		
	}
	
	@RequestMapping("/{id}")
	public String showEvent(@PathVariable("id") long id, Model model,@ModelAttribute("message") Message message){
		model.addAttribute("event",(Event)eventService.findById(id));
		
		return "showEvent";
	}
	
	@PostMapping("/{id}/messages/new")
	public String comment( @Valid @ModelAttribute("message") Message message, BindingResult res,@PathVariable("id") long event_id,HttpSession session,Model model) {
		System.out.println(message.getUser());
		if(res.hasErrors()) {return "redirect:/events/"+event_id;}
		message.setId(null);

		messageService.create(message);
		long user_id = (long)session.getAttribute("id");
		User u =(User)userService.findById(user_id);
		Event es = eventService.findById(event_id);
//		List<Message> msgs =es.getMessages();
//		msgs.add( message);
//		es.setMessages(msgs);
		
		model.addAttribute("commenter",u);
		message.setUser(u);
		message.setEvent(es);
		eventService.create(es);


		return "redirect:/events/"+event_id;
	}
	
	@PostMapping("/{id}/join")
	public String join(@PathVariable("id") long event_id, HttpSession session, Model model) {
		Event e = eventService.findById(event_id);
		long user_id = (long)session.getAttribute("id");
		User u =(User)userService.findById(user_id);
		if(e.isJoined()== true) {e.setJoined(false);eventService.update(e);
		}else if(e.isJoined() == false) {e.setJoined(true);eventService.update(e);}
		System.out.println(e.isJoined());

	
		List<User> users =e.getUsers();
		users.add(u);
		
		e.setUsers(users);
		
		 eventService.update(e);
		 return "redirect:/";
	}
	
	@PostMapping("/{id}/unjoin")
	public String unjoin(@PathVariable("id") long event_id, HttpSession session, Model model) {
		Event e = eventService.findById(event_id);
		long user_id = (long)session.getAttribute("id");
		User u =(User)userService.findById(user_id);
		if(e.isJoined()== true) {e.setJoined(false);eventService.update(e);
		}else if(e.isJoined() == false) {e.setJoined(true);eventService.update(e);}
		List<User> users =e.getUsers();
		users.remove(u);
		
		e.setUsers(users);
		
		 eventService.update(e);
		 return "redirect:/";
	}
}



