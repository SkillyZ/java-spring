package com.skilly.house.api.controller;

import java.util.List;

import com.skilly.house.api.common.*;
import com.skilly.house.api.model.House;
import com.skilly.house.api.model.User;
import com.skilly.house.api.model.UserMsg;
import com.skilly.house.api.service.AgencyService;
import com.skilly.house.api.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private AgencyService agencyService;

//  @Autowired
//  private CommentService commentService;


    @RequestMapping(value = "house/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", rcHouses);
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        return "/house/listing";
    }

    @RequestMapping(value = "house/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseDetail(long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
//        List<Comment> comments = commentService.getHouseComments(id);
        List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        if (house.getUserId() != null) {
            if (!Objects.equal(0L, house.getUserId())) {
                modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
            }
        }
        modelMap.put("house", house);
        modelMap.put("recomHouses", rcHouses);
//        modelMap.put("commentList", comments);
        return "/house/detail";
    }

    @RequestMapping(value = "house/leaveMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseMsg(UserMsg userMsg) {
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    @ResponseBody
    @RequestMapping(value = "house/rating", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultMsg houseRate(Double rating, Long id) {
        houseService.updateRating(id, rating);
        return ResultMsg.success();
    }


    @RequestMapping(value = "house/toAdd", method = {RequestMethod.POST, RequestMethod.GET})
    public String toAdd(ModelMap modelMap) {
        modelMap.put("citys", houseService.getAllCitys());
//        modelMap.put("communitys", houseService.getAllCommunitys());
        return "/house/add";
    }

    @RequestMapping(value = "house/add", method = {RequestMethod.POST, RequestMethod.GET})
    public String doAdd(House house) {
        User user = UserContext.getUser();
        houseService.addHouse(house, user);
        return "redirect:/house/ownlist?" + ResultMsg.successMsg("添加成功").asUrlParams();
    }

    @RequestMapping(value = "house/ownlist", method = {RequestMethod.POST, RequestMethod.GET})
    public String ownlist(House house, PageParams pageParams, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps", houseService.queryHouse(house, pageParams));
        modelMap.put("pageType", "own");
        return "/house/ownlist";
    }

    @RequestMapping(value = "house/del", method = {RequestMethod.POST, RequestMethod.GET})
    public String delsale(Long id, String pageType) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? false : true);
        return "redirect:/house/ownlist";
    }


    @RequestMapping(value = "house/bookmarked", method = {RequestMethod.POST, RequestMethod.GET})
    public String bookmarked(House house, PageParams pageParams, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.queryHouse(house, pageParams));
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }


    @RequestMapping(value = "house/bookmark", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultMsg bookmark(Long id, ModelMap modelMap) {
        User user = UserContext.getUser();
        houseService.bindUser2House(id, user.getId(), true);
        return ResultMsg.success();
    }

    @RequestMapping(value = "house/unbookmark", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultMsg unbookmark(Long id, ModelMap modelMap) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), true);
        return ResultMsg.success();
    }

}
