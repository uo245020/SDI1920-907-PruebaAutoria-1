package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private InvitationsService invitationsService;

	@PostConstruct
	public void init() {
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public List<User> getAllUsersButYourself(String email) {
		List<User> users = new ArrayList<User>();
		usersRepository.findAllButYourself(email).forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	

	public List<User> searchUsersByNameAndSurname(String searchText, String email) {
		List<User> users = new LinkedList<User>();
		users = usersRepository.searchByNameAndSurname(searchText, email);
		return users;
	}
	
	
	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findAll(pageable);
		return users;
	}

	public void addFriend(User sender, User receiver) {
		sender.addFriend(receiver);
		invitationsService.deleteInvitation(sender, receiver);
		
		
	}
	

	public Page<User> getUserFriends(Pageable pageable, User user) {
		return usersRepository.findUserFriends(pageable, user);
	}
	
	
}