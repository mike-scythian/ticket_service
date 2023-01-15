package test.tickets.service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ticket")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ticket_gen")
    @SequenceGenerator(name="ticket_gen", sequenceName = "ticket_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight", nullable = false)
    @JsonBackReference
    private Flight flight;

    @Column(name="payment_id")
    private Long paymentId;

}