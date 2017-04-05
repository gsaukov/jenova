package com.pro.jenova.rest.controller;

import org.springframework.web.bind.annotation.*;

@RestController("/health")
public class HeathCheckController {

    @RequestMapping(value = "/echo", method = RequestMethod.POST)
    @ResponseBody
    public String echo(@RequestBody String message) {
        return "Server Response: ".concat(message);
    }

}
