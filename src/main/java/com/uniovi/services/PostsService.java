package com.uniovi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private HttpSession httpSession;

	@SuppressWarnings("unchecked")
	public Post getPost(Long id) {
		Set<Post> consultedList = (Set<Post>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Post>();
		}
		Post obtainedPost = postsRepository.findById(id).get();
		consultedList.add(obtainedPost);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedPost;
	}

	public void addPost(Post post) {
		postsRepository.save(post);
	}

	public void deletePost(Long id) {
		postsRepository.deleteById(id);
	}

	public List<Post> getPostsForUser(User user) {
		return postsRepository.findAllByUser(user);
	}

}
