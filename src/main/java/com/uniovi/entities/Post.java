package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Post() {}

	public Post(String name, String description, User user) {
		super();
		this.title = name;
		this.text = description;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", name=" + title + ", description=" + text + ", user=" + user + "]";
	}

}
