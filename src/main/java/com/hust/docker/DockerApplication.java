package com.hust.docker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hust.docker.Mapper")
public class DockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class, args);
	}

}
