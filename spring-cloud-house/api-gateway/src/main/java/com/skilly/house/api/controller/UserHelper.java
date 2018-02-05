package com.skilly.house.api.controller;

import com.skilly.house.api.common.ResultMsg;
import com.skilly.house.api.model.User;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Range;

public class UserHelper {

    public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return ResultMsg.errorMsg("参数有误");
        }
        if (!Objects.equal(password, confirmPassword)) {
            return ResultMsg.errorMsg("密码必须与确认密码一致");
        }
        return ResultMsg.success();
    }

    public static ResultMsg validate(User account) {
        if (StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.errorMsg("Email有误");
        }
        if (StringUtils.isBlank(account.getName())) {
            return ResultMsg.errorMsg("名字有误");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPasswd()) || !account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码有误");
        }
        if (account.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码长度应大于6位");
        }
        if (account.getType() == null || !Range.closed(1, 2).contains(account.getType())) {
            return ResultMsg.errorMsg("类型有误");
        }
        return ResultMsg.success();
    }


}
