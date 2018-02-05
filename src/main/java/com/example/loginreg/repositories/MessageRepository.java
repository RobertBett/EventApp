package com.example.loginreg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.loginreg.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

}
