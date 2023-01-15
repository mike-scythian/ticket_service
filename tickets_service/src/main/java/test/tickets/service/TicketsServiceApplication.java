package test.tickets.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicketsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsServiceApplication.class, args);
	}

}
