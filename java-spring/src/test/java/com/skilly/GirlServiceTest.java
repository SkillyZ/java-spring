package com.skilly;

import com.skilly.Service.GirlService;
import com.skilly.entity.Girl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlServiceTest {
    @Autowired
    private GirlService girlService;
    @Test
    public void findOneTest() {
        Girl girl =girlService.findOne(2);
        Assert.assertEquals(new Integer(18), girl.getAge());
    }
}