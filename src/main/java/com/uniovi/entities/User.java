package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;
	private String password;

	@Transient
	private String passwordConfirm;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts;

	@ManyToMany
	@JoinTable(name = "friend")
	private Set<User> friends;

	@OneToMany(mappedBy = "usuarioEnvia")
	private Set<Invitation> sendedInvitations;

	@OneToMany(mappedBy = "usuarioRecibe")
	private Set<Invitation> receivedInvitations;

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public Set<Invitation> getSendedInvitations() {
		return sendedInvitations;
	}

	public Set<Invitation> getReceivedInvitations() {
		return receivedInvitations;
	}

	public void addFriend(User user) {
		friends.add(user);
		user.getFriends().add(this);
	}

	public boolean canInvite(String userEmail) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String authEmail = auth.getName();
		if (userEmail == authEmail)
			return false;
		for (User user : friends) {
			if (user.getEmail().equals(authEmail))
				return false;
		}
		for (Invitation invitation : sendedInvitations) {
			if (invitation.getUsuarioRecibe().getEmail().equals(authEmail))
				return false;
		}
		for (Invitation invitation : receivedInvitations) {
			if (invitation.getUsuarioEnvia().getEmail().equals(authEmail))
				return false;
		}
		return true;
	}

}