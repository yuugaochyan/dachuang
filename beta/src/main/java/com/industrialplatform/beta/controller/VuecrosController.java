package com.industrialplatform.beta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*@RequestParam("mail")String mail,@RequestParam("password")String password
        System.out.println(mail);
                System.out.println(password);
*/
@RestController
public class VuecrosController {
    @GetMapping("/vue")
    public String hivue( ){

        return "Hi!";
    }
}