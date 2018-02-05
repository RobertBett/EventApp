package com.example.loginreg.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.loginreg.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
		User findByEmail(String email);
}
