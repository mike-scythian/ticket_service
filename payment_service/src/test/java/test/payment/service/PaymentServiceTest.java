package test.payment.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.payment.service.model.Payment;
import test.payment.service.model.PaymentStatus;
import test.payment.service.repository.PaymentRepository;
import test.payment.service.service.PaymentService;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    private static Payment payment;
    private static String name;
    private static Double price;

    @BeforeAll
    static void init(){
        name = "John Snow";
        price = 50.0;
        payment = new Payment(null, PaymentStatus.NEW, name, price);
    }
    
    @Test
    void shouldGetCreatedPaymentId() {

        Long testPaymentId = paymentService.createPayment(name, price);

        assertThat(paymentRepository.findById(testPaymentId).orElseThrow())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(payment);

        paymentRepository.deleteById(testPaymentId);
    }
    @Test
    void shouldGetPaymentStatus(){

        Long paymentId = paymentRepository.save(payment).getId();
        assertThat(paymentService.getStatus(paymentId)).isIn(
                Arrays.stream(PaymentStatus.values()).
                        map(Enum::toString).
                        collect(Collectors.toList()));

        paymentRepository.deleteById(paymentId);
    }
    @Test
    void shouldGetRandomPaymentStatus(){

        Long paymentId = paymentRepository.save(payment).getId();

        assertThat(paymentService.statusSetup(paymentId)).isIn(PaymentStatus.values());

        paymentRepository.deleteById(paymentId);
    }
}
