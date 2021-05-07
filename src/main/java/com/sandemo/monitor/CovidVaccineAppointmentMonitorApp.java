package com.sandemo.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@EnableScheduling
@SpringBootApplication
public class CovidVaccineAppointmentMonitorApp {

	public static void main(String[] args) {

		SpringApplication.run(CovidVaccineAppointmentMonitorApp.class, args);
	}
}
