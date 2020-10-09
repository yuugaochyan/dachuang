package com.example.beta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VuecrosController {
    @GetMapping("/vue")
    public String hivue(@RequestParam("mail")String mail,@RequestParam("password")String password){
        System.out.println(mail);
        System.out.println(password);
        return "regist success!";
    }
}
