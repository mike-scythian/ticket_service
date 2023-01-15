package test.tickets.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.tickets.service.service.FlightService;
import test.tickets.service.service.TicketService;

@RestController
@RequestMapping("/ticket_service")
public class TicketController {

    @Autowired
    TicketService ticketService;
    @Autowired
    FlightService flightService;

    @GetMapping("/")
    public ResponseEntity<String> ping(){
        RestTemplate rt = new RestTemplate();
        String resp = rt.getForObject("http://localhost:8091/payment_service/", String.class);
        return ResponseEntity.ok(resp);
    }
    @GetMapping("/buying")
    public ResponseEntity<Long> buyTicket(@RequestParam String name, @RequestParam Long flightId){
        return ResponseEntity.ok(ticketService.buyTicket(name, flightId));
    }
}
