package com.skilly.house.hsrv.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.skilly.house.hsrv.common.RestResponse;
import com.skilly.house.hsrv.model.User;
import com.skilly.house.hsrv.service.GenericRest;
import com.skilly.house.hsrv.utils.Rests;

@Repository
public class UserDao {

    @Autowired
    private GenericRest rest;

    @Value("${user.service.name}")
    private String userServiceName;

    public User getAgentDetail(Long agentId) {
        RestResponse<User> response = Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/agentDetail" + "?id=" + agentId);
            ResponseEntity<RestResponse<User>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {
            });
            return responseEntity.getBody();
        });
        return response.getResult();
    }


}
