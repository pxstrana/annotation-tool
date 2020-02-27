package com.annotation.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller of the home calls, such as "/" or similar that show
 *  only public information of the app
 * @author Luis
 *
 */
@Controller
public class HomeController {
	
	
	/**
	 * Return the template of home.html
	 * @param model, any additional information that can be sended to the template
	 * @param principal, information in this case of the logged user
	 * @return The name of the template
	 */
	@RequestMapping("/home")
    public String home(Model model, Principal principal) {
       
        return "home";
    }

	/**
	 * Return the template of home.html by redirecting to "/home"
	 * @param model, any addition info that can be sended to the template
	 * @param principal, information of the user logged
	 * @return a redirection to "/home"
	 */
	@RequestMapping("/")
    public String index(Model model, Principal principal) {
       
        return "redirect:/home";
    }

}
