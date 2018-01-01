package com.skilly.controller;

import com.skilly.Service.GirlService;
import com.skilly.entity.Girl;
import com.skilly.entity.Result;
import com.skilly.repository.GirlRepository;
import com.skilly.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@RestController
public class GirlController {

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private GirlService girlService;

    @GetMapping("/girls")
    public List<Girl> girlList () {
        return girlRepository.findAll();
    }

    @GetMapping(value ="/girls/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id) {
        return girlRepository.findOne(id);
    }

    @PostMapping("/girls")
    public Result<Girl> add(@Valid Girl girl, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {//错误情况

            //return null;
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

//        girl.setCupSize(girl.getCupSize());
//        girl.setAge(girl.getAge());

        return ResultUtil.success(girlRepository.save(girl));
    }

//    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
//                        @RequestParam("age") Integer age) {
//        Girl girl = new Girl();
//        girl.setCupSize(cupSize);
//        girl.setAge(age);
//
//        return girlRepository.save(girl);
//    }

    @PutMapping(value ="/girls/{id}")
    public Girl girlUpdate (@PathVariable("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age) {

        Girl girl = new Girl();
        girl.setId(id);
        girl.setCupSize(cupSize);
        girl.setAge(age);

        return girlRepository.save(girl);
    }

    @DeleteMapping(value ="/girls/{id}")
    public void girlDelete (@PathVariable("id") Integer id) {
        girlRepository.delete(id);
    }

    //判断妹纸年级

    @GetMapping(value="girls/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception {
        girlService.getAge(id);
    }
}
