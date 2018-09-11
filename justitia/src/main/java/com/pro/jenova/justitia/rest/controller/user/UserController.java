package com.pro.jenova.justitia.rest.controller.user;

import com.pro.jenova.justitia.data.entity.User;
import com.pro.jenova.justitia.data.repository.UserRepository;
import com.pro.jenova.justitia.rest.controller.ErrorResponse;
import com.pro.jenova.justitia.rest.controller.RestResponse;
import com.pro.jenova.justitia.rest.controller.VoidResponse;
import com.pro.jenova.justitia.rest.controller.user.request.RestCreateUserRequest;
import com.pro.jenova.justitia.rest.controller.user.request.RestRemoveUserRequest;
import com.pro.jenova.justitia.rest.controller.user.response.RestListUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(@Valid @RequestBody RestCreateUserRequest restCreateUserRequest, BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        if (userRepository.existsByUsername(restCreateUserRequest.getUsername())) {
            return ErrorResponse.badRequest("USERNAME_ALREADY_EXISTS");
        }

        User user = userRepository.save(new User.Builder()
                .withUsername(restCreateUserRequest.getUsername())
                .withPassword(passwordEncoder.encode(restCreateUserRequest.getPassword()))
                .build());

        return VoidResponse.created();
    }

    @PreAuthorize("hasAuthority('REMOVE_USER')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(@Valid @RequestBody RestRemoveUserRequest restRemoveUserRequest, BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        if (userRepository.removeByUsername(restRemoveUserRequest.getUsername()) > 0L) {
            return VoidResponse.ok();
        } else {
            return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
        }
    }

    @PreAuthorize("hasAuthority('LIST_USERS')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> list() {
        List<String> usernames = userRepository.findAll().stream().map(User::getUsername).collect(toList());

        return new ResponseEntity<>(new RestListUsersResponse.Builder().withUsernames(usernames).build(), HttpStatus.OK);
    }

}
