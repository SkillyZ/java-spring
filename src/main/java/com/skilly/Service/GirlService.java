package com.skilly.Service;

import com.skilly.properties.GirlProperties;
import com.skilly.entity.Girl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@Service
public class GirlService {

    @Autowired
    private GirlProperties girlProperties;

    @Transactional
    public void insertTwo()
    {
        Girl girlA = new Girl();
        girlA.setAge(18);
        girlA.setCupSize("B");



    }
}
