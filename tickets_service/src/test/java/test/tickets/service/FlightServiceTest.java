package test.tickets.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.tickets.service.repository.FlightRepository;
import test.tickets.service.service.FlightService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class FlightServiceTest {
    @Autowired
    private FlightService flightService;
    @Autowired
    private FlightRepository flightRepository;

    @Test
    void shouldUpdateNumberOfTickets(){

        Long flightNumber = 1L;
        int decrement = 5;
        int tickets = flightRepository.findById(flightNumber).orElseThrow().getTicketsNumber();

        assertThat(flightService.updateNumberOfTickets(flightNumber, decrement))
                .isEqualTo(tickets-5);

        flightService.updateNumberOfTickets(flightNumber, (-1)*decrement);
    }
}
