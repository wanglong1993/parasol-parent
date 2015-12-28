package com.ginkgocap.parasol.oauth2.web.controller.mobile;

import com.ginkgocap.parasol.oauth2.domain.dto.UserJsonDto;
import com.ginkgocap.parasol.oauth2.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/m/")
public class MobileController {

    @Autowired
    private UserService userService;


    @RequestMapping("dashboard")
    public String dashboard() {
        return "mobile/dashboard";
    }

    @RequestMapping("user_info")
    @ResponseBody
    public UserJsonDto userInfo() throws Exception {
        return userService.loadCurrentUserJsonDto();
    }

}