package com.example.Slipper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String test(){

        return "main";
    }

    //테스트
    @GetMapping("/test11")
    public String test11(){

        return "test";
    }

}
