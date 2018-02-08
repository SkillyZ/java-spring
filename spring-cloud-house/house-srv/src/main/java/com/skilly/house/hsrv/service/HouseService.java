package com.mooc.house.hsrv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mooc.house.hsrv.common.BeanHelper;
import com.mooc.house.hsrv.common.HouseUserType;
import com.mooc.house.hsrv.common.LimitOffset;
import com.mooc.house.hsrv.dao.UserDao;
import com.mooc.house.hsrv.mapper.CityMapper;
import com.mooc.house.hsrv.mapper.HouseMapper;
import com.mooc.house.hsrv.model.City;
import com.mooc.house.hsrv.model.Community;
import com.mooc.house.hsrv.model.House;
import com.mooc.house.hsrv.model.HouseUser;
import com.mooc.house.hsrv.model.User;
import com.mooc.house.hsrv.model.UserMsg;

@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private com.mooc.house.hsrv.service.FileService fileService;

    @Autowired
    private com.mooc.house.hsrv.service.MailService mailService;

    @Autowired
    private UserDao userDao;


    @Value("${file.prefix}")
    private String imgPrefix;


    public List<House> queryAndSetImg(House query, LimitOffset pageParams) {
        List<House> houses = houseMapper.selectHouse(query, pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }


    public List<House> queryHousesByIds(List<Long> ids, Integer size) {
        House query = new House();
        query.setIds(ids);
        return queryAndSetImg(query, LimitOffset.build(size, 1));
    }


    public void updateHouse(House house) {
        houseMapper.updateHouse(house);
    }


    @Transactional(rollbackFor = Exception.class)
    public List<Community> getAllCommunitys() {
        Community community = new Community();
        return houseMapper.selectCommunity(community);
    }

    public List<City> getAllCitys() {
        City city = new City();
        return cityMapper.selectCitys(city);
    }


    /**
     * 1. 添加房产
     * 2. 绑定房产到用户关系
     *
     * @param house
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void addHouse(House house, Long userId) {
        BeanHelper.setDefaultProp(house, House.class);
        BeanHelper.onInsert(house);
        houseMapper.insert(house);
        bindUser2House(house.getId(), userId, HouseUserType.SALE);
    }


    @Transactional(rollbackFor = Exception.class)
    public void bindUser2House(Long id, Long userId, HouseUserType sale) {
        HouseUser existHouseUser = houseMapper.selectHouseUser(userId, id, sale.value);
        if (existHouseUser != null) {
            return;
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(id);
        houseUser.setUserId(userId);
        houseUser.setType(sale.value);
        BeanHelper.setDefaultProp(houseUser, HouseUser.class);
        BeanHelper.onInsert(houseUser);
        houseMapper.insertHouseUser(houseUser);
    }


    /**
     * 注意这里逻辑做了修改:当售卖时只能将房产下架，不能解绑用户关系
     * 当收藏时可以解除用户收藏房产这一关系
     * 解绑操作1.
     *
     * @param houseId
     * @param userId
     * @param houseUserType
     */
    public void unbindUser2Houser(Long houseId, Long userId, HouseUserType type) {
        if (type.equals(HouseUserType.SALE)) {
            houseMapper.downHouse(houseId);
        } else {
            houseMapper.deleteHouseUser(houseId, userId, type.value);
        }

    }


    public Pair<List<House>, Long> queryHouse(House query, LimitOffset build) {
        List<House> houses = Lists.newArrayList();
        House houseQuery = query;
        if (StringUtils.isNoneBlank(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communities = houseMapper.selectCommunity(community);
            if (!communities.isEmpty()) {
                houseQuery = new House();
                houseQuery.setCommunityId(communities.get(0).getId());
            }
        }
        houses = queryAndSetImg(houseQuery, build);
        Long count = houseMapper.selectHouseCount(houseQuery);
        return ImmutablePair.of(houses, count);
    }


    public House queryOneHouse(long id) {
        House query = new House();
        query.setId(id);
        List<House> houses = queryAndSetImg(query, LimitOffset.build(1, 0));
        if (!houses.isEmpty()) {
            return houses.get(0);
        }
        return null;
    }


    /**
     * 向经纪人发送留言通知
     *
     * @param userMsg
     */
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        BeanHelper.setDefaultProp(userMsg, UserMsg.class);
        houseMapper.insertUserMsg(userMsg);
        User user = userDao.getAgentDetail(userMsg.getAgentId());
        mailService.sendSimpleMail("来自用户" + userMsg.getEmail(), userMsg.getMsg(), user.getEmail());
    }


    public void updateRating(Long id, Double rating) {
        House house = queryOneHouse(id);
        Double oldRating = house.getRating();
        Double newRating = oldRating.equals(0D) ? rating : Math.min(Math.round(oldRating + rating) / 2, 5);
        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        houseMapper.updateHouse(updateHouse);
    }


}
