package com.skilly.Application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 12541 on 2017/12/29.
 */

@Controller
@RequestMapping("/blogs")
public class templateController {

    @RequestMapping("temp/{name}")
    public String hello (@PathVariable("name") String name, Model model)
    {
        model.addAttribute(model);
        return "hello";
    }

    @RequestMapping("/test2")
    public String greeting1() {
        return "Hello World templateController!";
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create() {
        return "mapping url is /blogs/create";
    }
}
