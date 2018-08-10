package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.data.repository.UserRepository;
import com.pro.jenova.omnidrive.messaging.user.UserEventProducer;
import com.pro.jenova.omnidrive.rest.controller.ErrorResponse;
import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import com.pro.jenova.omnidrive.rest.controller.VoidResponse;
import com.pro.jenova.omnidrive.rest.controller.user.request.RestCreateUser;
import com.pro.jenova.omnidrive.rest.controller.user.response.RestListUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventProducer userEventProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(@Valid @RequestBody RestCreateUser restCreateUser, BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        if (userRepository.existsByUsername(restCreateUser.getUsername())) {
            return ErrorResponse.badRequest("USERNAME_ALREADY_EXISTS");
        }

        User user = userRepository.save(new User.Builder()
                .withUsername(restCreateUser.getUsername())
                .withPassword(passwordEncoder.encode(restCreateUser.getPassword()))
                .build());

        userEventProducer.onUserCreated(user);

        return VoidResponse.created();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> list() {
        List<String> usernames = userRepository.findAll().stream().map(User::getUsername).collect(toList());

        return new ResponseEntity<>(new RestListUsers.Builder().withUsernames(usernames).build(), HttpStatus.OK);
    }

}
