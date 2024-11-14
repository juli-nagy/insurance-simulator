package com.insurancesimulator.insurance;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.insurancesimulator.config.SecurityConfiguration;
import com.insurancesimulator.insurance.model.CashWithdrawResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfiguration.class)
@WebMvcTest(InsuranceController.class)
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceService insuranceService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldWithdrawCashSuccessfully() throws Exception {
        Long insuranceId = 1L;
        Double cashValue = 50.0;
        Double newBalance = 150.0;

        CashWithdrawResponse mockResponse = new CashWithdrawResponse(cashValue, newBalance);
        when(insuranceService.withdraw(insuranceId, cashValue)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/insurance/withdraw/{insuranceId}/{cashValue}", insuranceId, cashValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                    String.format("Cash value of %f is withdrawn successfully. Your balance is %f",
                        cashValue, newBalance)
            ));

        verify(insuranceService, times(1)).withdraw(insuranceId, cashValue);
    }
}

