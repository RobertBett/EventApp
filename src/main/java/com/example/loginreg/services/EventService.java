package com.example.loginreg.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.loginreg.models.Event;
import com.example.loginreg.repositories.EventRepository;

@Service
public class EventService {
	private EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public void create(Event event) {
		eventRepository.save(event);
	}
	
	public ArrayList<Event>all(){
		return(ArrayList<Event>)eventRepository.findAll();
	}
	
	public Event findById(long id) {
		return(Event)eventRepository.findOne(id);
	}
	public void update(Event event) {
		eventRepository.save(event);
	}
	
	public void destroy(Event event) {
		eventRepository.delete(event);
	}
	
	public ArrayList<Event> findByState(String state){
		return(ArrayList<Event>)eventRepository.findByState(state);
	}
	
//	public ArrayList<Event> findNotByState(String state){
//		return(ArrayList<Event>)eventRepository.findByStateNotEquals(state);
//	}

}
