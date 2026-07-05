package com.home_rental_solution.ms_propiedades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPropiedadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPropiedadesApplication.class, args);
	}

}
