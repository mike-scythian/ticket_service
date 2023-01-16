package test.payment.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payment_id_gen")
    @SequenceGenerator(name = "payment_id_gen", sequenceName = "payment_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "amount")
    private Double amount;
}