package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

	@Autowired
	private InvitationsService invitationService;

	@RequestMapping("/user/list")
	public String getListado(Pageable pageable, Model model,
			@RequestParam(value = "", required = false) String searchText, Principal principal) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			searchText = "%" + searchText + "%";
			users = usersService.searchUsersByNameAndSurname(pageable, searchText, principal.getName());
		} else {
			users = usersService.getAllUsersButYourself(pageable, principal.getName());
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}

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
	public String login(Model model, String error) {
		if (error != null)
			model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = { "/sendFavorito/{id}" }, method = RequestMethod.GET)
	public String sendFavorito(Model model, @PathVariable Long id, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User sender = usersService.getUserByEmail(email);
		User receiver = usersService.getUser(id);
		sender.marcarfavorito(receiver.getEmail());
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = { "/delFavorito/{id}" }, method = RequestMethod.GET)
	public String delFavorito(Model model, @PathVariable Long id, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User sender = usersService.getUserByEmail(email);
		User receiver = usersService.getUser(id);
		sender.borrarfavorito(receiver.getEmail());
		return "redirect:/user/list";
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

	@RequestMapping(value = "multipleDelete", method = RequestMethod.POST)
	public String multipleDelete(Model model, HttpServletRequest request, ModelMap modelMap) {
		if (request.getParameterValues("userId") != null) {
			for (String id : request.getParameterValues("userId")) {

				usersService.deleteUser(Long.parseLong(id));
			}
		}
		return "redirect:/user/list";
	}

}