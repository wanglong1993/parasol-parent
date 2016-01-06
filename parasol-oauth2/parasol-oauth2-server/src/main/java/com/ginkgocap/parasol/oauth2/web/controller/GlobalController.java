package com.ginkgocap.parasol.oauth2.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/openapi/")
public class GlobalController {
	
//    @Value("#{properties['getIdentifyingCode']}")
//    private String getIdentifyingCode;
//
//    @RequestMapping("getIdentifyingCode")
//    public String overview(HttpServletRequest request) {
//        return "redirect:"+getIdentifyingCode;
//    }
}