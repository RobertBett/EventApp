package com.example.loginreg.repositories;


import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.loginreg.models.Event;

@Repository
public interface EventRepository  extends CrudRepository <Event,Long>{
	ArrayList<Event> findByState( String state); 
	
//	@Query(value="SELECT * FROM events WHERE state != ?1",nativeQuery=true)
//	ArrayList<Event> findByStateNotEquals(String state);
}
