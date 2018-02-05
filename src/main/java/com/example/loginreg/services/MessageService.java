package com.example.loginreg.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.loginreg.models.Message;
import com.example.loginreg.repositories.MessageRepository;

@Service
public class MessageService {
	private MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void create(Message message) {
		messageRepository.save(message);
	}
	
	public ArrayList<Message>all(){
		return (ArrayList<Message>)messageRepository.findAll();
	}

	public Message findById(long id) {
		return(Message)messageRepository.findOne(id);
	}
	
	public void update(Message message) {
		create(message);
	}
	public void destroy(Message message) {
		messageRepository.delete(message);
	}
	
}
