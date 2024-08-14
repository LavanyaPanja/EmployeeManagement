package com.employeeManagement.securityService.openfeign;

import com.employeeManagement.securityService.dto.ExpensesDto;
import com.employeeManagement.securityService.dto.LeavesDto;
import com.employeeManagement.securityService.dto.RequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "EXPENSES-SERVICE")
@Configuration
public interface ApiClientExpenses {
    @RequestMapping(method = RequestMethod.PUT, value = "/expenses/approve", consumes = "application/json")
    public String expensesApprove(RequestDto requestDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/expenses/reject", consumes = "application/json")
    public String expensesReject(RequestDto requestDto);

    @RequestMapping(method = RequestMethod.GET,value = "/expenses/hr/pending/list/{hrId}")
    public List<ExpensesDto> findExpensesByHrIdOpenFeign(@PathVariable long hrId);
}
