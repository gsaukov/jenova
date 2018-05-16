package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.rest.controller.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AppConfiguration appConfiguration;

    @RequestMapping(value = "/technical", method = RequestMethod.GET)
    public RestUser technical() {
        return new RestUser.Builder().withUsername(appConfiguration.getUsername()).withPassword(appConfiguration.getPassword()).build();
    }

}
