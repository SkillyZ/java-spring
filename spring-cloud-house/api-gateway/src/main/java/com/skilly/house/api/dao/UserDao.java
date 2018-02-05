package com.skilly.house.api.dao;

import com.google.common.collect.Lists;
import com.skilly.house.api.common.RestResponse;
import com.skilly.house.api.config.GenericRest;
import com.skilly.house.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@Repository
public class UserDao {

    @Autowired
    private GenericRest rest;

    @Value("${user.service.name}")
    private String userServiceName;

//    -----------------测试方法
    public String getusername(Long id) {
        String url = "http://user/user/getusername?id=" + id;
        return rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {} ).getBody().getResult();
    }


    public List<User> getUserList(User query) {
        ResponseEntity<RestResponse<List<User>>> resultEntity = rest.post("http://" + userServiceName + "/user/getList", query, new ParameterizedTypeReference<RestResponse<List<User>>>() {
        });
        RestResponse<List<User>> restResponse = resultEntity.getBody();
        if (restResponse.getCode() == 0) {
            return restResponse.getResult();
        } else {
            return Lists.newArrayList();
        }
    }


    public User addUser(User account) {
        String url = "http://" + userServiceName + "/user/add";
        ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, account, new ParameterizedTypeReference<RestResponse<User>>() {
        });
        RestResponse<User> response = responseEntity.getBody();
        if (response.getCode() == 0) {
            return response.getResult();
        }
        {
            throw new IllegalStateException("Can not add user");
        }

    }


    /**
     * 用户激活
     *
     * @param key
     */
    public boolean enable(String key) {

        String url = "http://" + userServiceName + "/user/add";
        ResponseEntity<RestResponse<Object>> resultEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {
        });
        RestResponse<Object> restResponse = resultEntity.getBody();
        return restResponse.getCode() == 0;
    }

}
