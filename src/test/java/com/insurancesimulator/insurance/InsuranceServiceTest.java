package com.insurancesimulator.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import com.insurancesimulator.exceptions.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccessfullyWithdrawAmountFromInsurance() {
        Long insuranceId = 1L;
        BigDecimal amount = BigDecimal.valueOf(50);
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        BigDecimal expectedNewBalance = initialBalance.subtract(amount);

        Insurance insurance = mock(Insurance.class);
        when(insuranceRepository.findById(insuranceId)).thenReturn(Optional.of(insurance));

        String expectedMessage = String.format(
            "Cash value of %.3f is withdrawn successfully. Your balance is %.3f",
            amount,
            expectedNewBalance
        );

        when(insurance.withdraw(amount)).thenReturn(
            new CashWithdrawResponse(amount, expectedNewBalance));

        CashWithdrawResponse response = insuranceService.withdraw(insuranceId, amount);

        assertEquals(expectedMessage, response.getMessage());
        verify(insurance).withdraw(amount);
        verify(insuranceRepository).save(insurance);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfInsuranceDoesNotExist() {
        Long nonExistentId = 99L;
        when(insuranceRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
            insuranceService.withdraw(nonExistentId, BigDecimal.valueOf(50))
        );
        assertTrue(exception.getMessage().contains("Insurance"));
    }
}
