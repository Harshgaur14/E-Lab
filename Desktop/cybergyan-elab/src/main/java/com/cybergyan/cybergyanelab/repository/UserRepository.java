package com.cybergyan.cybergyanelab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybergyan.cybergyanelab.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username,String password);
	
}
