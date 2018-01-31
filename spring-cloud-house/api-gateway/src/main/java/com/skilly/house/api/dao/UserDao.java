package com.skilly.house.api.dao;

import com.skilly.house.api.common.RestResponse;
import com.skilly.house.api.config.GenericRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@Repository
public class UserDao {

    @Autowired
    private GenericRest rest;

    public String getusername(Long id) {
        String url = "http://user/getusername?id=" + id;
        return rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {} ).getBody().getResult();
    }
}
