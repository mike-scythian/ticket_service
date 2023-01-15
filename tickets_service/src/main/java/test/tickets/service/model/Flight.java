package test.tickets.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bus_flight")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "point_departure", nullable = false)
    @NonNull
    private String pointDeparture;
    @Column(name = "pointArrival", nullable = false)
    @NonNull
    private String pointArrival;
    @Column(name = "departure_time")
    @NonNull
    private LocalDateTime departureTime;
    @Column(name = "price", nullable = false)
    @NonNull
    private Double price;
    @Column(name = "tickets_amount")
    @NonNull
    private Integer ticketsNumber;
    @OneToMany(mappedBy = "flight")
    private Set<Ticket> ticketSet;

    @NoArgsConstructor
    public static class FlightBuilder{
        private String pointDeparture;
        private String pointArrival;
        private LocalDateTime departureTime;
        private Double price;
        private Integer ticketsQuantity;
        public FlightBuilder pointDeparture(String pointDeparture){
            this.pointDeparture = pointDeparture;
            return this;
        }
        public FlightBuilder pointArrival(String pointArrival){
            this.pointArrival = pointArrival;
            return this;
        }
        public FlightBuilder departureTime(LocalDateTime departureTime){
            this.departureTime = departureTime;
            return this;
        }
        public FlightBuilder price(Double price){
            this.price = price;
            return this;
        }
        public FlightBuilder ticketsQuantity(Integer ticketsQuantity){
            this.ticketsQuantity = ticketsQuantity;
            return this;
        }
        public Flight build(){
            return new Flight(pointDeparture, pointArrival, departureTime, price, ticketsQuantity);
        }
    }
}
