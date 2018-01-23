package com.skilly.house.biz.service;

import com.google.common.collect.Lists;
import com.skilly.house.biz.mapper.UserMapper;
import com.skilly.house.common.model.User;
import com.skilly.house.common.utils.BeanHelper;
import com.skilly.house.common.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    public List<User> getUsers()
    {
        return userMapper.selectUsers();
    }



    /**
     * 1.插入数据库，非激活;密码加盐md5;保存头像文件到本地 2.生成key，绑定email 3.发送邮件给用户
     *
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insert(account);
        registerNotify(account.getEmail());
        return true;
    }

    private void registerNotify(String email) {
        
    }
}
