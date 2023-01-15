package test.tickets.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name = "ticket")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight", nullable = false)
    private Flight flight;

    @Column(name="payment_id")
    private Long paymentId;
    private PaymentStatusEnumeration paymentStatus;

}