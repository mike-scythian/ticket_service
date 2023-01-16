package test.payment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.payment.service.controller.PaymentController;
import test.payment.service.dto.PaymentDTO;
import test.payment.service.model.PaymentStatus;
import test.payment.service.service.PaymentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentWebApiTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Test
    void shouldCreateNewPayment(){

        when(paymentService.createPayment(any(String.class), any(Double.class))).thenReturn(Long.getLong("100L"));
        ResponseEntity<Long> paymentId = paymentController.createPayment(new PaymentDTO("Peter Crouch", 100.0));

        assertThat(paymentId.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void shouldSetupRandomStatus(){

        when(paymentService.statusSetup(any(Long.class))).thenReturn(PaymentStatus.randomGenerator());

        ResponseEntity<String> status =paymentController.setupStatus(any(Long.class));
        assertThat(status.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void shouldGetStatus(){

        when(paymentService.getStatus(any(Long.class))).thenReturn(PaymentStatus.randomGenerator().toString());

        ResponseEntity<String> status =paymentController.getStatus(any(Long.class));
        assertThat(status.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
