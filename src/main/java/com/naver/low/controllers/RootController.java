package com.naver.low.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
    @RequestMapping("^(?!/api).*$")
    public String forward() {
        return "index";
    }
}
