package test.payment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentWebApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static String restMapUrl = "/payment_service";

    @Test
    void shouldReturnStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(restMapUrl+"/status/get").content("1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string())

}
