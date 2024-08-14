package com.employeeManagement.expensesService.openfeign;

import com.employeeManagement.expensesService.dto.ExpensesNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "NOTIFICATION-SERVICE")
@Configuration
public interface ApiClientExpensesNotification {
    @RequestMapping(method = RequestMethod.GET,value = "/notification/expenses/employee/{employeeId}")
    public List<ExpensesNotificationDto> findByIdEmployeeIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/notification/expenses/create", consumes = "application/json")
    public ExpensesNotificationDto expensesNotificationAdd(ExpensesNotificationDto expensesNotificationDto);
}
