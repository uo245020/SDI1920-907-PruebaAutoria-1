package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;

public interface InvitationsRepository extends CrudRepository<Invitation,Long> {
	
	Page<Invitation> findAll(Pageable pageable);

	@Query("SELECT n FROM Invitation n WHERE n.usuarioRecibe = ?1")
	Page<Invitation> searchReceivedInvitationsByUser(Pageable pageable, User user);

	@Query("SELECT n FROM Invitation n WHERE n.usuarioEnvia = ?1 AND n.usuarioRecibe = ?2")
	Invitation searchInvitationByCoupleUsers(User sender, User receiver);

}
