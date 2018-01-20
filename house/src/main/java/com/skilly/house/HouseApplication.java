package com.skilly.house;

import com.skilly1.house.autoconfig.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableHttpClient
public class HouseApplication {

	public static void fizzBuzz()
	{
		List<Integer> fivethree = new ArrayList<Integer>();
		List<Integer> three = new ArrayList<Integer>();
		List<Integer> five = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				fivethree.add(i);
			} else if (i % 3 == 0) {
				three.add(i);
			} else if (i % 5 == 0) {
				five.add(i);
			}
		}
		System.out.println(fivethree);
		System.out.println(three);
		System.out.println(five);
	}

	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}
}
