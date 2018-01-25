package com.skilly.house.biz.service;

import com.skilly.house.biz.mapper.AgencyMapper;
import com.skilly.house.common.model.Agency;
import com.skilly.house.common.model.User;
import com.skilly.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/25.
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 访问user表获取详情 添加用户头像
     *
     * @param userId
     * @return
     */
    public User getAgentDeail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> list = agencyMapper.selectAgent(user, PageParams.build(1, 1));
        setImg(list);
        if (!list.isEmpty()) {
            User agent = list.get(0);
            //将经纪人关联的经纪机构也一并查询出来
            Agency agency = new Agency();
            agency.setId(agent.getAgencyId().intValue());
            List<Agency> agencies = agencyMapper.select(agency);
            if (!agencies.isEmpty()) {
                agent.setAgencyName(agencies.get(0).getName());
            }
            return agent;
        }
        return null;
    }

    public void setImg(List<User> imgs) {
        imgs.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }
}
