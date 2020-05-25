package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitationsRepository;

@Service
public class InvitationsService {
	
	@Autowired
	private InvitationsRepository invitationsRepository;


	public void sendInvitation(User whoSended, User whoReceived) {
		Invitation invitation = getInvitation(whoSended, whoReceived);
		if (!whoReceived.getEmail().equals(whoSended.getEmail()) && !whoReceived.getFriends().contains(whoSended)
				&& invitation == null) {
			invitationsRepository.save(new Invitation(whoSended, whoReceived));
			return;
		}
	}

	public Invitation getInvitation(User whoSended, User whoReceived) {
		return invitationsRepository.searchInvitationByCoupleUsers(whoSended, whoReceived);
	}

	public Page<Invitation> getReceivedInvitationsByUser(Pageable pageable, User user) {
		return invitationsRepository.searchReceivedInvitationsByUser(pageable, user);
	}

	public void deleteInvitation(User whoSended, User whoReceived) {
		invitationsRepository.delete(getInvitation(whoSended, whoReceived));
	}
	
	
}
