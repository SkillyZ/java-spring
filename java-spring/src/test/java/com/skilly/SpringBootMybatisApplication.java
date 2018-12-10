package com.skilly;

import com.skilly.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMybatisApplication implements CommandLineRunner {

    @Autowired
    private PersonMapper personMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.personMapper.getById(2));
    }
}