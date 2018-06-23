package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.data.repository.UserRepository;
import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import com.pro.jenova.omnidrive.rest.controller.VoidResponse;
import com.pro.jenova.omnidrive.rest.controller.error.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestResponse create(@Valid @RequestBody RestCreateUser restCreateUser, BindingResult violations) {
        if (violations.hasErrors()) {
            return BadRequest.dueTo(violations);
        }

        userRepository.save(new User.Builder()
                .withUsername(restCreateUser.getUsername())
                .withPassword(restCreateUser.getPassword())
                .build());

        return VoidResponse.CREATED;
    }

}
