package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.data.repository.UserRepository;
import com.pro.jenova.omnidrive.rest.controller.common.RestResponse;
import com.pro.jenova.omnidrive.rest.controller.error.RestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> register(@RequestBody RestUser restUser) {
        if (userRepository.findByUsername(restUser.getUsername()).isPresent()) {
            return usernameExists(restUser.getUsername());
        }

        return success(saveUser(restUser));
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> findByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username).map(user -> success(user)).orElse(usernameNotFound(username));
    }

    private User saveUser(RestUser restUser) {
        return userRepository.save(new User.Builder().withUsername(restUser.getUsername()).withPassword
                (restUser.getPassword()).build());
    }

    private ResponseEntity<RestResponse> success(User user) {
        return new ResponseEntity<>(new RestUser.Builder().withUsername(user.getUsername()).withPassword(user.
                getPassword()).build(), HttpStatus.OK);
    }

    private ResponseEntity<RestResponse> usernameNotFound(String username) {
        return new ResponseEntity<>(new RestError.Builder().withErrorCode("USERNAME_NOT_FOUND").withMessage(
                format("A user with username [%s] could not be found.", username)).build(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<RestResponse> usernameExists(String username) {
        return new ResponseEntity<>(new RestError.Builder().withErrorCode("USERNAME_ALREADY_EXISTS").withMessage(format
                ("A user with username [%s] already exists.", username)).build(), HttpStatus.BAD_REQUEST);
    }

}
