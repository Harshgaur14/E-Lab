package com.cybergyan.cybergyanelab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cybergyan.cybergyanelab.entities.User;
import com.cybergyan.cybergyanelab.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
	
		User user = userRepository.findByUsername(username);

//		System.out.println("From DB");
//		System.out.println(user.toString());
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();//.getCredentials().toString();//.getPrincipal();
//		System.out.println(auth.getPrincipal().toString());
//		System.out.println(auth.getCredentials().toString());

		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		return new CustomUserDetails(user);
	}
	
	public User logindetails(String username, String password) {
		User user =userRepository.findByUsernameAndPassword(username,password);
		return user;
	}
	
	
	
	}

