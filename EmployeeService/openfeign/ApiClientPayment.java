package com.employeeManagement.employeeService.openfeign;


import com.employeeManagement.employeeService.dto.SalaryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PAYMENT-SERVICE")
@Configuration
public interface ApiClientPayment {
    @RequestMapping(method = RequestMethod.GET,value = "/payment/employee/{empId}")
    public SalaryDto findByIdForSalaryOpenFeign(@PathVariable long empId);
}
