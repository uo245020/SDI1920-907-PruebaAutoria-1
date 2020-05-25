package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) " 
	+ "OR LOWER(u.lastName) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1)"
	+ "AND u.email NOT LIKE ?2 AND u.email NOT LIKE 'admin@email.com')")
	Page<User> searchByNameAndSurname(Pageable pageable, String searchtext, String email);
	
	@Query("SELECT r.friends FROM User r WHERE r = ?1")
	Page<User> findUserFriends(Pageable pageable, User user);

	@Query("SELECT u FROM User u WHERE u.email NOT LIKE ?1 AND u.email NOT LIKE 'admin@email.com'")
	Page<User> findAllButYourself(Pageable pageable, String email);
	
}
