package com.insurancesimulator.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.insurancesimulator.insurance.model.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import exceptions.EntityNotFoundException;
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
        Double amount = 50.0;
        Double initialBalance = 100.0;
        Double expectedNewBalance = initialBalance - amount;

        Insurance insurance = mock(Insurance.class);
        when(insuranceRepository.findById(insuranceId)).thenReturn(Optional.of(insurance));
        when(insurance.withdraw(amount)).thenReturn(new CashWithdrawResponse(amount, expectedNewBalance));

        CashWithdrawResponse response = insuranceService.withdraw(insuranceId, amount);

        assertEquals(amount, response.getCashWithdrawn());
        assertEquals(expectedNewBalance, response.getBalance());
        verify(insurance).withdraw(amount);
        verify(insuranceRepository).save(insurance);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfInsuranceDoesNotExist() {
        Long nonExistentId = 99L;
        when(insuranceRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
            insuranceService.withdraw(nonExistentId, 50.0)
        );
        assertTrue(exception.getMessage().contains("Insurance"));
    }
}
