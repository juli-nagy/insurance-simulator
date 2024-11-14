package com.insurancesimulator.insurance;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import com.insurancesimulator.insurance.model.request.CashWithdrawRequest;
import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import com.insurancesimulator.insurance.model.response.InsuranceListResponse;
import com.insurancesimulator.utils.models.shared.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<ApiResponse> registerNewInsurance(@RequestBody Insurance insurance){
        insuranceService.addInsurance(insurance);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse("Insurance entity was created", HttpStatus.CREATED));
    }

    @GetMapping
    public ResponseEntity<InsuranceListResponse> getAllInsurances(){
        List<Insurance> foundInsurance = insuranceService.getAllInsurances();
        return foundInsurance.isEmpty() ? new ResponseEntity<>(NOT_FOUND)
            : ResponseEntity.ok(new InsuranceListResponse(
                "Insurances found",
                foundInsurance.size(),
                foundInsurance)
            );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<InsuranceListResponse> getInsurancesByCustomerId(
        @PathVariable Long customerId){
        List<Insurance> foundInsurance = insuranceService.getAllInsurancesByCustomerId(customerId);
        return foundInsurance.isEmpty()
            ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
            : ResponseEntity.ok(new InsuranceListResponse(
                "Insurances found for customer",
                foundInsurance.size(),
                foundInsurance)
            );
    }

    @PutMapping("/withdraw")
    public ResponseEntity<CashWithdrawResponse> withdrawCash(@RequestBody CashWithdrawRequest request){
        CashWithdrawResponse cashWithdraw =
            insuranceService.withdraw(request.getInsuranceId(), request.getCashValue());
        return ResponseEntity
            .ok(cashWithdraw);
    }

    @DeleteMapping(path = "{insuranceId}")
    public ResponseEntity<ApiResponse> deleteInsurance(@PathVariable("insuranceId") Long insuranceId){
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.ok(
            new ApiResponse("Insurance entity was deleted", HttpStatus.OK)
        );
    }

    @GetMapping("/api/test/exception")
    public String triggerException() {
        throw new RuntimeException("Test exception"); // Should be caught by handleGenericException
    }
}
