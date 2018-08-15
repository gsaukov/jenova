package com.pro.jenova.omnidrive.rest.controller.authority;

import com.pro.jenova.omnidrive.data.entity.Authority;
import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.data.repository.AuthorityRepository;
import com.pro.jenova.omnidrive.data.repository.UserRepository;
import com.pro.jenova.omnidrive.rest.controller.ErrorResponse;
import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import com.pro.jenova.omnidrive.rest.controller.VoidResponse;
import com.pro.jenova.omnidrive.rest.controller.authority.request.RestCreateAuthorityRequest;
import com.pro.jenova.omnidrive.rest.controller.authority.request.RestListAuthoritiesRequest;
import com.pro.jenova.omnidrive.rest.controller.authority.request.RestRemoveAuthorityRequest;
import com.pro.jenova.omnidrive.rest.controller.authority.response.RestListAuthoritiesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(
            @Valid @RequestBody RestCreateAuthorityRequest restCreateAuthorityRequest,
            BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        Optional<User> user = userRepository.findByUsername(restCreateAuthorityRequest.getUsername());

        if (!user.isPresent()) {
            return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
        }

        if (authorityRepository.existsByUserAndAuthority(
                user.get(), restCreateAuthorityRequest.getAuthority())) {
            return ErrorResponse.badRequest("AUTHORITY_ALREADY_EXISTS");
        }

        authorityRepository.save(
                new Authority.Builder()
                        .withUser(user.get())
                        .withAuthority(restCreateAuthorityRequest.getAuthority())
                        .build());

        return VoidResponse.created();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(
            @Valid @RequestBody RestRemoveAuthorityRequest restRemoveAuthorityRequest,
            BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        Optional<User> user = userRepository.findByUsername(restRemoveAuthorityRequest.getUsername());

        if (!user.isPresent()) {
            return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
        }

        if (authorityRepository.removeByUserAndAuthority(
                user.get(), restRemoveAuthorityRequest.getAuthority())
                > 0L) {
            return VoidResponse.ok();
        } else {
            return ErrorResponse.badRequest("AUTHORITY_NOT_FOUND");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> list(
            @Valid @RequestBody RestListAuthoritiesRequest restListAuthoritiesRequest,
            BindingResult violations) {
        if (violations.hasErrors()) {
            return ErrorResponse.badRequest(violations);
        }

        Optional<User> user = userRepository.findByUsername(restListAuthoritiesRequest.getUsername());

        if (!user.isPresent()) {
            return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
        }

        List<String> authorities =
                authorityRepository
                        .findByUser(user.get())
                        .stream()
                        .map(Authority::getAuthority)
                        .collect(toList());

        return new ResponseEntity<>(
                new RestListAuthoritiesResponse.Builder().withAuthorities(authorities).build(),
                HttpStatus.OK);
    }
}
