/**
 *
 */
package com.skilly.house.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.skilly.house.api.common.ResultMsg;
import com.skilly.house.api.common.UserContext;
import com.skilly.house.api.model.User;
import com.skilly.house.api.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//

/**
 * @author eric
 */
@Controller
public class UserController {


    @Autowired
    private AccountService accountService;

//  @Autowired
//  private AgencyService agencyService;


//----------------------------注册流程-------------------------------------------


    @RequestMapping(value = "accounts/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String accountsSubmit(User account, ModelMap modelMap) {
        if (account == null || account.getName() == null) {
//            modelMap.put("agencyList", agencyService.getAllAgency());
            return "/user/accounts/register";
        }
        ResultMsg retMsg = UserHelper.validate(account);

        if (retMsg.isSuccess()) {
            boolean exist = accountService.isExist(account.getEmail());
            if (!exist) {
                accountService.addAccount(account);
                modelMap.put("success_email", account.getEmail());
                return "/user/accounts/registerSubmit";
            } else {
                return "redirect:/accounts/register?" + ResultMsg.errorMsg("邮箱已被占用").asUrlParams();
            }
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("参数错误").asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = accountService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
        }
    }


    //----------------------------登录流程-------------------------------------------



}

