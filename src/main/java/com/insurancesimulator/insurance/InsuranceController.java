package com.insurancesimulator.insurance;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import com.insurancesimulator.insurance.model.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<String> registerNewInsurance(@RequestBody Insurance insurance){
        insuranceService.addInsurance(insurance);
        return ResponseEntity.status(HttpStatus.CREATED).body("Insurance entity was created");
    }

    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances(){
        List<Insurance> foundInsurance = insuranceService.getAllInsurances();
        return foundInsurance.isEmpty() ? new ResponseEntity<>(NOT_FOUND)
            : ResponseEntity.ok(foundInsurance);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Insurance>> getInsurancesByCustomerId(
        @PathVariable Long customerId){
        List<Insurance> foundInsurance = insuranceService.getAllInsurancesByCustomerId(customerId);
        return foundInsurance.isEmpty() ? new ResponseEntity<>(NOT_FOUND)
            : ResponseEntity.ok(foundInsurance);
    }
    @GetMapping("/withdraw/{insuranceId}/{cashValue}")
    public ResponseEntity<String> withdrawCash(@PathVariable("insuranceId") Long insuranceId,
        @PathVariable("cashValue") Double cashValue){
        CashWithdrawResponse cashWithdraw = insuranceService.withdraw(insuranceId, cashValue);
        return ResponseEntity.ok().body(String.format("Cash value of %f is withdrawn successfully. "
            + "Your balance is %f", cashWithdraw.getCashWithdrawn(), cashWithdraw.getBalance()));
    }

    @DeleteMapping(path = "{insuranceId}")
    public ResponseEntity<String> deleteInsurance(@PathVariable("insuranceId") Long insuranceId){
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.ok().body("Insurance entity was deleted");
    }
}
