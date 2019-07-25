package com.winning.devops.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 22:45
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String toIndex(){
        return  "index";
    }
}
