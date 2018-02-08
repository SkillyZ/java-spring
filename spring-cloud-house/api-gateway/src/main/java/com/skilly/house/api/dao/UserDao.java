package com.skilly.house.api.dao;

import com.google.common.collect.Lists;
import com.skilly.house.api.common.RestResponse;
import com.skilly.house.api.config.GenericRest;
import com.skilly.house.api.model.Agency;
import com.skilly.house.api.model.ListResponse;
import com.skilly.house.api.model.User;
import com.skilly.house.api.utils.Rests;
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
        return rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
        }).getBody().getResult();
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


    public User authUser(User user) {
        String url = "http://" + userServiceName + "/user/auth";
        ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, user, new ParameterizedTypeReference<RestResponse<User>>() {
        });
        RestResponse<User> response = responseEntity.getBody();
        if (response.getCode() == 0) {
            return response.getResult();
        }
        {
            throw new IllegalStateException("Can not add user");
        }
    }

    public void logout(String token) {
        String url = "http://" + userServiceName + "/user/logout?token=" + token;
        rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {
        });
    }

    public User getUserByTokenFb(String token) {
        return new User();
    }

    public User getUserByToken(String token) {
        String url = "http://" + userServiceName + "/user/get?token=" + token;
        ResponseEntity<RestResponse<User>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {
        });
        RestResponse<User> response = responseEntity.getBody();
        if (response == null || response.getCode() != 0) {
            return null;
        }
        return response.getResult();
    }


    public List<Agency> getAllAgency() {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/list");
            ResponseEntity<RestResponse<List<Agency>>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<List<Agency>>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }

    public User updateUser(User user) {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/user/update");
            ResponseEntity<RestResponse<User>> responseEntity =
                    rest.post(url, user, new ParameterizedTypeReference<RestResponse<User>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }

    public User getAgentById(Long id) {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/agentDetail?id=" + id);
            ResponseEntity<RestResponse<User>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }


    public Agency getAgencyById(Integer id) {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/agencyDetail?id=" + id);
            ResponseEntity<RestResponse<Agency>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<Agency>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }

    public void addAgency(Agency agency) {
        Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/add");
            ResponseEntity<RestResponse<Object>> responseEntity =
                    rest.post(url, agency, new ParameterizedTypeReference<RestResponse<Object>>() {
                    });
            return responseEntity.getBody();
        });
    }

    public ListResponse<User> getAgentList(Integer limit, Integer offset) {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/agency/agentList?limit=" + limit + "&offset=" + offset);
            ResponseEntity<RestResponse<ListResponse<User>>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<ListResponse<User>>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }

    public String getEmail(String key) {
        return Rests.exc(() -> {
            String url = Rests.toUrl(userServiceName, "/user/getKeyEmail?key=" + key);
            ResponseEntity<RestResponse<String>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
                    });
            return responseEntity.getBody();
        }).getResult();
    }
}
