package test.tickets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.tickets.service.repository.TicketRepository;
import test.tickets.service.service.TicketService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void shouldCreateNewTicket(){
        String clientName = "Arnold Schwarzenegger";
        long flight = 4L;

        assertThat(ticketRepository.
                findById(ticketService.buyTicket(clientName, flight))
                .orElseThrow()
                .getFlight()
                .getId())
                .isEqualTo(flight);
    }

    @Test
    void shouldReturnInfoAboutTicket() throws JsonProcessingException {
        Long ticket = 10L;
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JsonNode node = mapper.readTree(ticketService.getTicketInfo(ticket));

        assertAll(
                () -> assertThat(node.get("point_departure").asText()).isEqualTo("Chacago"),
                () -> assertThat(node.get("point_arrival").asText()).isEqualTo("LasVegas"),
                () -> assertThat(LocalDateTime.parse(node.get("departure_time").asText()))
                        .isEqualTo(LocalDateTime.of(2023,1,25,7,15)),
                () -> assertThat(node.get("price").asDouble()).isEqualTo(500.0),
                () -> assertThat(node.get("payment_status").asText()).isEqualTo("DONE"));
    }
}
