package com.arion.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.arion.app.**.mapper")
@EnableScheduling
public class ArionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArionApplication.class, args);
	}

}
