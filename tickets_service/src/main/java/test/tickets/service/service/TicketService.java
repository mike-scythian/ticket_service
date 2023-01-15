package test.tickets.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import test.tickets.service.dto.PaymentDTO;
import test.tickets.service.model.Flight;
import test.tickets.service.model.Ticket;
import test.tickets.service.repository.FlightRepository;
import test.tickets.service.repository.TicketRepository;

import java.time.format.DateTimeFormatter;
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
    private final String setStatusPaymentURL = "http://localhost:" + PAYMENT_SERVICE_PORT + "/payment_service/status/set";
    private final String getStatusPaymentURL = "http://localhost:" + PAYMENT_SERVICE_PORT + "/payment_service/status/get";
    public Long buyTicket(String clientName, long flightId){

        PaymentDTO paymentDTO = createPaymentDto(clientName, flightId);
        RestTemplate restRequest = new RestTemplate();
        FlightService flightService = new FlightService(flightRepository);

        Long paymentId = restRequest.postForEntity(createPaymentURL, paymentDTO, Long.class).getBody();
        String paymentStatus = restRequest.postForEntity(setStatusPaymentURL,paymentId, String.class).getBody();
        Long ticketId = createTicket(flightId,paymentId,paymentStatus).getId();
        flightService.updateNumberOfTickets(flightId, 1);

        return ticketId;
    }
    public String getTicketInfo(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        Flight flight = flightRepository.findById(ticket.getFlight().getId()).orElseThrow();
        ObjectMapper jsonMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectNode resultInfo = jsonMapper.createObjectNode();

        resultInfo.put("point_departure", flight.getPointDeparture());
        resultInfo.put("point_arrival", flight.getPointArrival());
        resultInfo.put("departure_time", flight.getDepartureTime().format(DateTimeFormatter.ISO_DATE_TIME));
        resultInfo.put("price", flight.getPrice());
        resultInfo.put("payment_status", getPaymentStatus(ticket.getPaymentId()));
        try {
            String resultJson = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultInfo);
            return resultJson;
        }catch(JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    @Scheduled(fixedRate = 1000)
    public int statusChecker(){
        List<Ticket> tickets = ticketRepository.findAll();
        for(Ticket t : tickets) {
            String tempStatus = checkTicketStatus(t);
            if(tempStatus.equals("NEW"))
                switch (checkTicketStatus(t)) {
                    case "DONE" -> {
                        return 0;
                    }
                    case "FAILED" -> {
                        return getTicketsAmountOnFlight(t.getId());
                    }
                }
        }
        return -1;
    }
    private Ticket createTicket(Long flightId, Long paymentId, String paymentStatus){
        Ticket ticket = new Ticket();
        ticket.setFlight(flightRepository.findById(flightId).orElseThrow());
        ticket.setPaymentId(paymentId);
        //ticket.setPaymentStatus("NEW");
        ticketRepository.save(ticket);
        return ticket;
    }
    private PaymentDTO createPaymentDto(String name, Long flightId){

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setClientName(name);
        paymentDTO.setAmount(flightRepository.findById(flightId).orElseThrow().getPrice());

        return paymentDTO;
    }
    private String getPaymentStatus(Long paymentId){

       return new RestTemplate().postForEntity(getStatusPaymentURL,paymentId,String.class).getBody();
    }
    private String checkTicketStatus(Ticket ticket) {

        RestTemplate restRequest = new RestTemplate();
        String status = getPaymentStatus(ticket.getPaymentId());
        if(status.equals("NEW"))
            return restRequest.postForEntity(setStatusPaymentURL, ticket.getPaymentId(), String.class).getBody();
        return status;
    }
    private int getTicketsAmountOnFlight(Long ticketId){

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        Flight flight = flightRepository.findById(ticket.getFlight().getId()).orElseThrow();

        return flight.getTicketsNumber();
    }
}
