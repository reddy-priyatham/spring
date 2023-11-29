package com.kpr.springsecurity1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/open")
    public String open(){
        return "This is open";
    }
    @RequestMapping("/user")
    public String user(){
        return "This is user";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "This is admin";
    }
    @RequestMapping("/none")
    public String none(){
        return "This is none";
    }
}
