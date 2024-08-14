package com.employeeManagement.paymentsService.openfeign;

import com.employeeManagement.paymentsService.dto.SalaryNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "NOTIFICATION-SERVICE")
@Configuration
public interface ApiClientSalaryNotification {
    @RequestMapping(method = RequestMethod.GET,value = "/notification/salary/expenses/employee/{employeeId}")
    public List<SalaryNotificationDto> findByIdEmployeeIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/notification/salary/expenses/create", consumes = "application/json")
    public SalaryNotificationDto salaryNotificationAdd(SalaryNotificationDto salaryNotificationDto);
}
