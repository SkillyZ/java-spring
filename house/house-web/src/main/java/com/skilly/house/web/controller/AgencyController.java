package com.skilly.house.web.controller;

import com.google.common.base.Objects;
import com.skilly.house.biz.service.AgencyService;
import com.skilly.house.biz.service.HouseService;
import com.skilly.house.biz.service.MailService;
import com.skilly.house.common.constants.CommonConstants;
import com.skilly.house.common.model.Agency;
import com.skilly.house.common.model.House;
import com.skilly.house.common.model.User;
import com.skilly.house.common.page.PageData;
import com.skilly.house.common.page.PageParams;
import com.skilly.house.common.result.ResultMsg;
import com.skilly.house.web.interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/25.
 */
@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

//    @Autowired
//    private RecommendService recommendService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MailService mailService;

    @RequestMapping("agency/create")
    public String agencyCreate(){
        User user =  UserContext.getUser();
        if (user == null || !Objects.equal(user.getEmail(), "spring_boot@163.com")) {
            return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
        }
        return "/user/agency/create";
    }


    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize,Integer pageNum,ModelMap modelMap){
        if (pageSize == null) {
            pageSize = 6;
        }
        PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
//        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
//        modelMap.put("recomHouses", houses);
        modelMap.put("ps", ps);
        return "/user/agent/agentList";
    }

    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id,ModelMap modelMap){
        User user =  agencyService.getAgentDeail(id);
//        List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        House query = new House();
        query.setUserId(id);
        query.setBookmarked(false);
        PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3,1));
        if (bindHouse != null) {
            modelMap.put("bindHouses", bindHouse.getList()) ;
        }
//        modelMap.put("recomHouses", houses);
        modelMap.put("agent", user);
        modelMap.put("agencyName", user.getAgencyName());
        return "/user/agent/agentDetail";
    }

    @RequestMapping("/agency/agentMsg")
    public String agentMsg(Long id,String msg,String name,String email, ModelMap modelMap){
        User user =  agencyService.getAgentDeail(id);
        mailService.sendMail("咨询", "email:"+email+",msg:"+msg, user.getEmail());
        return "redirect:/agency/agentDetail?id="+id +"&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }




}
