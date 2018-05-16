package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestUser register(@RequestBody RestUser restUser) {
        return new RestUser.Builder().withUsername(restUser.getUsername()).withPassword(restUser.getPassword()).build();
    }

}
