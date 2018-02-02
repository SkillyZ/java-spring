package com.skilly.house.user.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.skilly.house.user.common.UserException;
import com.skilly.house.user.mapper.UserMapper;
import com.skilly.house.user.model.User;
import com.skilly.house.user.utils.BeanHelper;
import com.skilly.house.user.utils.HashUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

@Service
public class UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;


    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 1.首先通过缓存获取
     * 2.不存在将从通过数据库获取用户对象
     * 3.将用户对象写入缓存，设置缓存时间5分钟
     * 4.返回对象
     *
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        String key = "user:" + id;
        String json = redisTemplate.opsForValue().get(key);
        User user = null;
        if (Strings.isNullOrEmpty(json)) {
            user = userMapper.selectById(id);
            user.setAvatar(imgPrefix + user.getAvatar());
            String string = JSON.toJSONString(user);
            redisTemplate.opsForValue().set(key, string);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        } else {
            user = JSON.parseObject(json, User.class);
        }
        return user;
    }

    public List<User> getUserByQuery(User user) {
        List<User> users = userMapper.select(user);
        users.forEach(u -> {
            u.setAvatar(imgPrefix + u.getAvatar());
        });
        return users;
    }


    /**
     * 注册
     *
     * @param user
     * @param enableUrl
     * @return
     */
    public boolean addAccount(User user, String enableUrl) {
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        BeanHelper.onInsert(user);
        userMapper.insert(user);
        registerNotify(user.getEmail(), enableUrl);
        return true;
    }

    /**
     * 发送注册激活邮件
     *
     * @param email
     * @param enableUrl
     */
    private void registerNotify(String email, String enableUrl) {
        String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
        String content = enableUrl + "?key=" + randomKey;
        mailService.sendSimpleMail("房产平台激活邮件", content, email);
    }

    public boolean enable(String key) {
        String email = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(email)) {
            throw new UserException(UserException.Type.USER_NOT_FOUND, "无效的key");
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.update(updateUser);
        return true;
    }

}
