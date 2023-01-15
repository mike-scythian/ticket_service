package test.tickets.service.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import test.tickets.service.dto.PaymentDTO;
import test.tickets.service.model.Flight;
import test.tickets.service.model.PaymentStatusEnumeration;
import test.tickets.service.model.Ticket;
import test.tickets.service.repository.FlightRepository;
import test.tickets.service.repository.TicketRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final FlightRepository flightRepository;
    private final int PAYMENT_SERVICE_PORT = 8091;
    private final String createPaymentURL = "http://localhost:" + PAYMENT_SERVICE_PORT + "/payment_service/";
    private final String getStatusPaymentURL = "http://localhost:" + PAYMENT_SERVICE_PORT + "/payment_service/status";

    public Long buyTicket(String clientName, long flightId){

        PaymentDTO paymentDTO = createPaymentDto(clientName, flightId);
        RestTemplate restRequest = new RestTemplate();
        FlightService flightService = new FlightService(flightRepository);

        Long paymentId = restRequest.postForEntity(createPaymentURL, paymentDTO, Long.class).getBody();
        String paymentStatus = restRequest.postForEntity(getStatusPaymentURL,paymentId, String.class).getBody();
        Long ticketId = createTicket(flightId,paymentId,paymentStatus).getId();
        flightService.updateNumberOfTickets(flightId, 1);

        return ticketId;
    }
    //@Scheduled(fixedRate = 10000)
    public int statusChecker(){
        List<Ticket> tickets = ticketRepository.findAll();
        for(Ticket t : tickets)
            switch (t.getPaymentStatus()){
                case DONE -> {return 0;}
                case FAILED -> {return getTicketsAmountOnFlight(t.getId());}
                default -> {
                    checkTicketStatus(t);
                }
            }
        return -1;
    }
    private Ticket createTicket(Long flightId, Long paymentId, String paymentStatus){
        Ticket ticket = new Ticket();
        ticket.setFlight(flightRepository.findById(flightId).orElseThrow());
        ticket.setPaymentId(paymentId);
        ticket.setPaymentStatus(PaymentStatusEnumeration.valueOf(paymentStatus));
        ticketRepository.save(ticket);
        return ticket;
    }
    private PaymentDTO createPaymentDto(String name, Long flightId){

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setClientName(name);
        paymentDTO.setAmount(flightRepository.findById(flightId).orElseThrow().getPrice());

        return  paymentDTO;
    }
    private PaymentStatusEnumeration checkTicketStatus(Ticket ticket) {
        RestTemplate restRequest = new RestTemplate();

       if(ticket.getPaymentStatus().equals(PaymentStatusEnumeration.NEW)) {
            PaymentStatusEnumeration status = PaymentStatusEnumeration.valueOf(
                    restRequest.postForEntity(getStatusPaymentURL, ticket.getPaymentId(), String.class).getBody());
           ticket.setPaymentStatus(status);
        }
        return ticket.getPaymentStatus();
    }
    private int getTicketsAmountOnFlight(Long ticketId){

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        Flight flight = flightRepository.findById(ticket.getFlight().getId()).orElseThrow();

        return flight.getTicketsNumber();
    }
}
