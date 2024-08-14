package com.employeeManagement.employeeService.openfeign;

import com.employeeManagement.employeeService.dto.ExpensesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "EXPENSES-SERVICE")
@Configuration
public interface ApiClientExpenses {
    @RequestMapping(method = RequestMethod.GET,value = "/expenses/{id}")
    public ExpensesDto findByIdForExpensesOpenFeign(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/expenses/list/{employeeId}")
    public List<ExpensesDto> findByIdEmployeeIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.GET,value = "/expenses/pending/list/{employeeId}")
    public List<ExpensesDto> findByIdEmployeeIdWithStatusOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/expenses/create", consumes = "application/json")
    public ExpensesDto createExpensesByEmployee(ExpensesDto expensesDto);

    @RequestMapping(method = RequestMethod.DELETE,value = "/expenses/{id}")
    public String deleteExpensesById(@PathVariable long id);

}
