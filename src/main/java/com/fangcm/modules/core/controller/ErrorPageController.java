package com.fangcm.modules.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorPageController {

    @RequestMapping(value = {"404"})
    public String put() {
        return "404";
    }
}