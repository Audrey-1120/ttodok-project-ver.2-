package com.suyuri.ttodokproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //기본 페이지 요청 메소드
    @GetMapping("/")
    public String index() { //인덱스 페이지를 요청하는 API
        return "login";
    }
}
