package com.donacion.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CiberDonacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiberDonacionesApplication.class, args);
	}

}
