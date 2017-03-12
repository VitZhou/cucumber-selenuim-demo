package com.demo.cs.login;


import com.google.common.base.Strings;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/login.json/{userName}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public String login(@PathVariable String userName, @PathVariable String password) {
        System.out.println(userName);
        System.out.println(password);
        if (Strings.isNullOrEmpty(userName) || Strings.isNullOrEmpty(password)) {
            return "FAIL";
        }
        return "SUCCESS";
    }
}
