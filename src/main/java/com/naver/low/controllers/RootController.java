package com.naver.low.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
    @RequestMapping(value = "/*")
    public String redirect() {
        return "index";
    }
}
