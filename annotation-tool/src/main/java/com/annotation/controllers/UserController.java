package com.annotation.controllers;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.annotation.entities.User;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDoesNotExistsException;
import com.annotation.services.impl.UsersServiceImpl;

/**
 * Controller class of user related actions.
 * 
 * @author Luis
 *
 */
@Controller
public class UserController {

	private static final String REDIRECT_USER_LIST="redirect:/user/list";
	private static final String ERROR_VAR= "error";
	
	@Autowired
	UsersService usersService = new UsersServiceImpl();

	/**
	 * Receives the call and return the template with the users of the app
	 * 
	 * @param model,    the information sended to the template is stored in the
	 *                  model
	 * @param principal
	 * @return
	 */
	/*
	@GetMapping("/user/list")
	public String listUsers(Model model, Principal principal) {

		model.addAttribute("usersList", usersService.getUsers());
		return "user/list";
	}
	*/

	@GetMapping(value = "/user/add")
	public String addUserView(Model model, Principal principal) {

		return "user/add";
	}

//	@PostMapping(value = "/user/add")
//	public String addUser(Model model, @Validated User user, BindingResult result) {
//
//	
//		try {
//			usersService.addUser(user);
//		} catch (UserAlreadyExistException e) {
//			model.addAttribute(ERROR_VAR, true);
//			return "user/add";
//		}
//		return REDIRECT_USER_LIST;
//	}

	@RequestMapping("/user/delete/{id}")
	public String deleteUser(Model model,@PathVariable Long id) {
		try {
			usersService.deleteUser(id);
		} catch (UserDoesNotExistsException e) {
			model.addAttribute(ERROR_VAR, true);
		}
		return REDIRECT_USER_LIST;
	}

	@GetMapping("/user/update/{id}")
	public String updateUserView(Model model,@PathVariable Long id) throws UserDoesNotExistsException {
		try {
			User user = usersService.getUserById(id);
			model.addAttribute("user",user);
		} catch (NoSuchElementException e) {
			return REDIRECT_USER_LIST;
		}

		return "user/update";

	}
	
	/**
	 * Updates user by changing the role using id as identifier
	 * @param model
	 * @param user
	 * @param result
	 * @return
	 */
	@PostMapping("/user/update/{id}")
	public String updateUser(Model model, @Validated User user, BindingResult result,@PathVariable Long id) {
		try {
			System.out.println(user.getId());
			usersService.updateUser(user);
		}catch (Exception e) {
			model.addAttribute("error", true);
		}
		
		
		return REDIRECT_USER_LIST;
		
	}

//	@RequestMapping("/login")
//	public String login(Model model) {
//		return "user/login";
//	}
}
