package com.employeeManagement.employeeService.openfeign;


import com.employeeManagement.employeeService.dto.ExpensesNotificationDto;
import com.employeeManagement.employeeService.dto.LeaveNotificationDto;
import com.employeeManagement.employeeService.dto.SalaryNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "NOTIFICATION-SERVICE")
@Configuration
public interface ApiClientNotification {
    @RequestMapping(method = RequestMethod.GET,value = "/notification/employee/{empId}")
    public List<LeaveNotificationDto> leavesNotification(@PathVariable long empId);
    @RequestMapping(method = RequestMethod.GET,value = "/notification/expenses/employee/{empId}")
    public List<ExpensesNotificationDto> expensesNotification(@PathVariable long empId);
    @RequestMapping(method = RequestMethod.GET,value = "/notification/salary/expenses/employee/{empId}")
    public List<SalaryNotificationDto> salaryNotification(@PathVariable long empId);
}
