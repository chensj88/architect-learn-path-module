package com.winning.devops.cloud.controller;

import com.winning.devops.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 23:20
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/save")
    public String save(Model model){
        String save = userService.save();
        model.addAttribute("save",save);
        return "list";
    }
}
