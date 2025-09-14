package com.SebasBarber.SebasBarber_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class SebasBarberBackendApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SebasBarberBackendApplication.class, args);

		System.out.println(LocalDate.now().lengthOfMonth());
	}

}
