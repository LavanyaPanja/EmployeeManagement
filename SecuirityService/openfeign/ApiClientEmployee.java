package com.employeeManagement.securityService.openfeign;

import com.employeeManagement.securityService.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE")
@Configuration
public interface ApiClientEmployee {
    @RequestMapping(method = RequestMethod.GET,value = "/employee/{id}")
    public EmployeeDto findByIdForEmployeeOpenFeign(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/list/{hrId}")
    public List<EmployeeDto> allListEmployeeByHr(@PathVariable long hrId);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/manager/list/{managerId}")
    public List<EmployeeDto> allListEmployeeByManager(@PathVariable long managerId);
    @RequestMapping(method = RequestMethod.POST, value = "/employee/create", consumes = "application/json")
    public EmployeeDto createEmployeeByHR(EmployeeDto employeeDto);

    @RequestMapping(method = RequestMethod.DELETE,value = "/employee/{id}")
    public String deleteEmployeeById(@PathVariable long id);
    //expenses
    @RequestMapping(method = RequestMethod.GET,value = "/employee/expenses/{id}")
    public ExpensesDto findByIdForExpensesOpenFeign(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/expenses/list/{employeeId}")
    public List<ExpensesDto> findByIdExpensesIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/expenses/pending/list/{employeeId}")
    public List<ExpensesDto> findByIdExpensesIdWithStatusOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/employee/expenses/create", consumes = "application/json")
    public String createExpensesByEmployee(ExpensesDto expensesDto);

    @RequestMapping(method = RequestMethod.DELETE,value = "/employee/expenses/{id}")
    public String deleteExpensesById(@PathVariable long id);

    //leaves
    @RequestMapping(method = RequestMethod.GET,value = "/employee/leaves/{id}")
    public LeavesDto findByIdForLeavesOpenFeign(@PathVariable long id);
    @RequestMapping(method = RequestMethod.GET,value = "/employee/leaves/list/{employeeId}")
    public List<LeavesDto> findByIdLeavesIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/leaves/pending/list/{employeeId}")
    public List<LeavesDto> findByIdLeavesIdWithStatusOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/employee/leaves/request", consumes = "application/json")
    public String createLeavesByEmployee(LeavesDto leavesDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/employee/leaves/edit", consumes = "application/json")
    public String editLeavesOpenFeign(LeavesDto leavesDto);

    @RequestMapping(method = RequestMethod.DELETE,value = "/employee/leaves/{id}")
    public String deleteLeavesById(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/total/leaves/{employeeId}")
    public TotalLeavesDto totalLeavesId(@PathVariable long employeeId);

    //salary
    @RequestMapping(method = RequestMethod.GET,value = "/employee/salary/{empId}")
    public SalaryDto findByIdForSalaryOpenFeign(@PathVariable long empId);

    @RequestMapping(method = RequestMethod.GET,value = "/employee/leave/notification/{empId}")
    public List<LeaveNotificationDto> leavesNotification(@PathVariable long empId);
    @RequestMapping(method = RequestMethod.GET,value = "/employee/expenses/notification/{empId}")
    public List<ExpensesNotificationDto> expensesNotification(@PathVariable long empId);
    @RequestMapping(method = RequestMethod.GET,value = "/employee/salary/notification/{empId}")
    public List<SalaryNotificationDto> salaryNotification(@PathVariable long empId);

}
