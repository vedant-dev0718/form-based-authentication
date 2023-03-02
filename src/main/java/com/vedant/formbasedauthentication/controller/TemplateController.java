package com.vedant.formbasedauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TemplateController {

    private static final String PATH = "login";

    @GetMapping(value = PATH)
    public String getLoginView(){
        return "login";
    }
}
