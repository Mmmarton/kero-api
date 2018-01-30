package com.komak.kero.keroapi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "You are ok.";
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String login2() {
        return "You are ok. 2";
    }
}
