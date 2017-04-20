package com.pro.jenova.omnidrive.rest.controller.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestUser register(@RequestBody RestUser restUser) {
        return new RestUser.Builder().withUsername("username").withPassword("password").withEmail("email").build();
    }

}
