package com.skilly.Service;

import com.skilly.entity.Girl;
import com.skilly.enums.ResultEnum;
import com.skilly.excecption.GirlException;
import com.skilly.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@Service
public class GirlService {
    @Autowired
    public GirlRepository girlRepository;

    @Transactional
    public void insertTwo() {
        Girl girlA = new Girl();
        girlA.setCupSize("G");
        girlA.setAge(22);
        girlA.setMoney((double) 10);
        girlRepository.save(girlA);
        Girl girlB = new Girl();
        girlB.setCupSize("Hss");
        girlB.setAge(22);
        girlB.setMoney((double) 10);
        girlRepository.save(girlB);
    }

    public void getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        Integer age = girl.getAge();
        if (age < 10) {
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        } else if (age > 10 && age < 16) {
            throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
        }
    }

    /**
     * 通过ID查询妹纸的信息
     */
    public Girl findOne(Integer id) {
        return girlRepository.findOne(id);
    }
}