package com.insurancesimulator.insurance;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.insurancesimulator.config.SecurityConfiguration;
import com.insurancesimulator.insurance.model.request.CashWithdrawRequest;
import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(SecurityConfiguration.class)
@WebMvcTest(InsuranceController.class)
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceService insuranceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void withdrawCashSuccessfullyTest() throws Exception {
        // Given
        Long insuranceId = 1L;
        BigDecimal cashValue = BigDecimal.valueOf(50.0);
        BigDecimal newBalance = BigDecimal.valueOf(150.0);

        CashWithdrawRequest request = new CashWithdrawRequest();
        request.setCashValue(cashValue);

        CashWithdrawResponse mockResponse = new CashWithdrawResponse(cashValue, newBalance);

        when(insuranceService.withdraw(insuranceId, cashValue)).thenReturn(mockResponse);

        String requestJson = objectMapper.writeValueAsString(request);
        String expectedJsonResponse = objectMapper.writeValueAsString(mockResponse);

        mockMvc.perform(put("/api/insurance/{insurance}/withdraw", insuranceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedJsonResponse));

        verify(insuranceService, times(1)).withdraw(insuranceId, cashValue);
    }

}
