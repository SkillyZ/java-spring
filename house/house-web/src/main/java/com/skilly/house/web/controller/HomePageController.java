package com.skilly.house.web.controller;

import com.skilly.house.biz.service.RecommendService;
import com.skilly.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/29.
 */
@Controller
public class HomePageController {

    @Autowired
    private RecommendService recommendService;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        List<House> houses = recommendService.getLastest();
        modelMap.put("recomHouses", houses);
        return "homepage/index";
    }

    @RequestMapping("")
    public String home(ModelMap modelMap) {

        return "redirect:/index";
    }
}
