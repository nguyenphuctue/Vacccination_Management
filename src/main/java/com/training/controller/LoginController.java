package com.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.training.utils.Constant.*;

@Controller
public class LoginController {
		
	@GetMapping(value = {"/login"})
    public String showLogin() {
        return LOGIN;
    }

    @GetMapping(value = {"/home"})
    public String homePage() {
        return HOME;
    }
    
    @GetMapping(value = {"/logout"})
    public String showLogout(Model model) {
        return LOGIN;
    }
    
    @GetMapping(value = {"/403"})
    public String error403() {
      return "403";
    }
    
    @GetMapping(value = {"/404"})
    public String error404() {
      return "404";
    }

}
