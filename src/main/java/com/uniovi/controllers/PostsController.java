package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.CreatePostValidator;

@Controller
public class PostsController {
	@Autowired
	private PostsService postsService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private CreatePostValidator createPostValidator;

	@RequestMapping("/post/list")
	public String getList(Model model, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		model.addAttribute("postsList", postsService.getPostsForUser(user));
		return "post/list";
	}

	@RequestMapping(value = "/post/add")
	public String getMark(Model model) {
		// model.addAttribute("usersList", usersService.getUsers());
		model.addAttribute("post", new Post());
		return "post/add";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(Model model, Principal principal, @Validated Post post, BindingResult result) {
		createPostValidator.validate(post, result);
		model.addAttribute("post", post);
		if (result.hasErrors()) {
			return "post/add";
		}
		User user = usersService.getUserByEmail(principal.getName());
		post.setUser(user);
		post.setDate(new Date());
		postsService.addPost(post);
		return "redirect:/post/list";
	}

	@RequestMapping("/post/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("post", postsService.getPost(id));
		return "post/details";
	}

	@RequestMapping("/post/delete/{id}")
	public String deletePost(@PathVariable Long id) {
		postsService.deletePost(id);
		return "redirect:/post/list";
	}

	@RequestMapping(value = "/post/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("post", postsService.getPost(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "post/edit";
	}

	@RequestMapping(value = "/post/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Post post) {
		Post original = postsService.getPost(id);
		original.setTitle(post.getTitle());
		original.setText(post.getText());
		postsService.addPost(original);
		return "redirect:/post/details/" + id;
	}

//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String goHome() {
//		return "/";
//	}

	@RequestMapping("/post/friendList/{email}")
	public String geFriendtList(Model model, @PathVariable String email,
			@RequestParam(value = "", required = false) String searchText) {
		System.out.println("aaaaa");
		// List<Post> posts = new ArrayList<>();
		User user = usersService.getUserByEmail(email);
//				if (searchText != null && !searchText.isEmpty()) {
//					searchText = "%" + searchText + "%";
//					
//				} else {
//					posts = postsService.getPostsForUser(user);
//				}
		model.addAttribute("friendPostList", postsService.getPostsForUser(user));
		return "post/friendList";
	}

}
