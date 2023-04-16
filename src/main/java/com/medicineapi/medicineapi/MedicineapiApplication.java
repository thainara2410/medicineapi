package com.medicineapi.medicineapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MedicineapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicineapiApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("12345678"));
	}

}
