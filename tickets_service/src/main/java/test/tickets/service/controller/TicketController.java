package test.tickets.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.tickets.service.service.TicketService;

@RestController
@RequestMapping("/ticket_service")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/buying")
    public ResponseEntity<Long> buyTicket(@RequestParam String name,
                                          @RequestParam Long flightId){

        return ResponseEntity.ok(ticketService.buyTicket(name, flightId));
    }
    @GetMapping("/ticket")
    public ResponseEntity<String> ticketInfo(@RequestParam Long ticketId){

        return ResponseEntity.ok(ticketService.getTicketInfo(ticketId));
    }
}
