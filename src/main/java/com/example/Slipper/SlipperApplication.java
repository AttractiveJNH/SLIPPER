package com.example.Slipper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing  // 자동시간 생성에 필요한 어노테이션
public class SlipperApplication {


	public static void main(String[] args) {
		SpringApplication.run(SlipperApplication.class, args); // 자동시간 생성에 필요한 코드
	}

}
