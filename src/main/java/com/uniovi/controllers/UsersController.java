package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
//	@Autowired
//	private LogInFormValidator logInFormValidator;
	
	@Autowired
	private InvitationsService invitationService;

	@RequestMapping("/user/list")
	public String getListado(Model model, @RequestParam(value = "", required = false) String searchText, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		List<User> users = new ArrayList<User>();
		List<User> usersAuxiliar = new ArrayList<User>();
		if (searchText != null && !searchText.isEmpty()) {
			searchText = "%" + searchText + "%";
			users = usersService.searchUsersByNameAndSurname(searchText, principal.getName());
		} else {
			users = usersService.getAllUsersButYourself(principal.getName());
		}
		for (int i=0;i<users.size();i++) {
			if(users.get(i).getEmail()!= email) {
				//Comprobamos que a el usuario no le aparece el mismo
				usersAuxiliar.add(users.get(i));
			}
		}
		users=usersAuxiliar;
		model.addAttribute("usersList", users);
		return "user/list";
	}

//	@RequestMapping(value = "/user/add")
//	public String getUser(Model model) {
//		model.addAttribute("usersList", usersService.getUsers());
//		return "user/add";
//	}
//
//	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
//	public String setUser(@ModelAttribute User user) {
//		usersService.addUser(user);
//		return "redirect:/user/list";
//	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}
	

//	@RequestMapping(value = "/user/edit/{id}")
//	public String getEdit(Model model, @PathVariable Long id) {
//		User user = usersService.getUser(id);
//		model.addAttribute("user", user);
//		return "user/edit";
//	}
//
//	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
//		user.setId(id);
//		usersService.addUser(user);
//		return "redirect:/user/details/" + id;
//	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(Model model, @Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		model.addAttribute("user", user);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		//model.addAttribute("user", new User());
		return "login";
	}


	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	
	@RequestMapping(value = { "/sendInvitation/{id}" }, method = RequestMethod.GET)
	public String sendInvitation(Model model, @PathVariable Long id, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User sender = usersService.getUserByEmail(email);
		User receiver = usersService.getUser(id);
		invitationService.sendInvitation(sender, receiver);
		Page<User> users = usersService.getUsers(pageable);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = { "/user/invitationsList" }, method = RequestMethod.GET)
	public String seeInvitations(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		Page<Invitation> invitations = invitationService.getReceivedInvitationsByUser(pageable, activeUser);
		model.addAttribute("invitationsList", invitations.getContent());
		model.addAttribute("page", invitations);
		return "user/invitationsList";
	}
	
	@RequestMapping(value = { "/acceptInvitation/{id}" }, method = RequestMethod.GET)
	public String acceptInvitation(Model model, @PathVariable Long id, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User receiver = usersService.getUserByEmail(email);
		User sender = usersService.getUser(id);
		usersService.addFriend(sender, receiver);
		Page<Invitation> invitations = invitationService.getReceivedInvitationsByUser(pageable, receiver);
		model.addAttribute("invitationsList", invitations.getContent());
		model.addAttribute("page", invitations);
		return "user/invitationsList";
	}
	
	
	@RequestMapping(value = { "/user/friendsList" }, method = RequestMethod.GET)
	public String seeFriendsList(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		Page<User> friends = usersService.getUserFriends(pageable, activeUser);
		model.addAttribute("friendsList", friends.getContent());
		model.addAttribute("page", friends);
		return "user/friendsList";
	}
	
	

}