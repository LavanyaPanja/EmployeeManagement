package com.employeeManagement.expensesService.openfeign;


import com.employeeManagement.expensesService.dto.UpdatePayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PAYMENT-SERVICE")
@Configuration
public interface ApiClientPayment {
    @RequestMapping(method = RequestMethod.PUT, value = "/payment/expenses/amount", consumes = "application/json")
    public String amountUpdate(UpdatePayload updatePayload);
}
