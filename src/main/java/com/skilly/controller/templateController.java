package com.skilly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 12541 on 2017/12/29.
 */

@Controller
public class templateController {

    @RequestMapping("hello/{name}")
    public String hello (@PathVariable("name") String name, Model model)
    {
        model.addAttribute(model);
        return "hello";
    }

}
