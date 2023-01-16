package test.tickets.service.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.tickets.service.model.Flight;
import test.tickets.service.repository.FlightRepository;

@Service
@AllArgsConstructor
public class FlightService {
    @Autowired
    private final FlightRepository flightRepository;

    public Integer updateNumberOfTickets(Long flightId, int decrement){

        Flight flight = flightRepository.findById(flightId).orElseThrow();
        Integer newTicketsNumber = flight.getTicketsNumber() - decrement;
        if(newTicketsNumber < 0)
            newTicketsNumber = 0;
        flight.setTicketsNumber(newTicketsNumber);

        return flightRepository.save(flight).getTicketsNumber();
    }
}
