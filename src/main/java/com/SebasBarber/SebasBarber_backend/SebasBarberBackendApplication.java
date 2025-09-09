package com.SebasBarber.SebasBarber_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class SebasBarberBackendApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SebasBarberBackendApplication.class, args);

		LocalTime hour = LocalTime.of(18,0);
		System.out.println(hour.plusMinutes(100));
	}

}
