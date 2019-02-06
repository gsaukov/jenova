package com.pro.jenova.exodus.rest.controller.login;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exodus-api/login")
public class LoginController {

    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public String greet(Model model) {
        model.addAttribute("message", "Welcome, please enter your login name to proceed.");
        return "greet";
    }

}
