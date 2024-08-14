package com.employeeManagement.securityService.openfeign;

import com.employeeManagement.securityService.dto.EmployeeDto;
import com.employeeManagement.securityService.dto.ExpensesMainDto;
import com.employeeManagement.securityService.dto.LeavesDto;
import com.employeeManagement.securityService.dto.SalaryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE")
@Configuration
public interface ApiClientSalary {

    @RequestMapping(method = RequestMethod.GET,value = "/payment/list")
    public List<SalaryDto> findAllSalaryAccounts();
    @RequestMapping(method = RequestMethod.GET,value = "/payment/{id}")
    public SalaryDto findByAccountId(@PathVariable long id);
    @RequestMapping(method = RequestMethod.DELETE,value = "/payment/{id}")
    public String deleteSalaryAccount(@PathVariable long id);
    @RequestMapping(method = RequestMethod.POST, value = "/payment/create", consumes = "application/json")
    public SalaryDto createSalaryAccount(SalaryDto salaryDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/payment/update", consumes = "application/json")
    public SalaryDto updateSalaryAccount(SalaryDto salaryDto);

    @RequestMapping(method = RequestMethod.GET,value = "/payment/expenses/list")
    public List<ExpensesMainDto> findAllExpensesAccounts();
    @RequestMapping(method = RequestMethod.DELETE,value = "/payment/expenses/{id}")
    public String deleteExpensesAccount(@PathVariable long id);
    @RequestMapping(method = RequestMethod.POST, value = "/payment/expenses/create", consumes = "application/json")
    public ExpensesMainDto createExpensesAccount(ExpensesMainDto expensesMainDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/payment/expenses/update", consumes = "application/json")
    public ExpensesMainDto updateExpensesAccount(ExpensesMainDto expensesMainDto);

    @RequestMapping(method = RequestMethod.GET,value = "/payment/expenses/{id}")
    public ExpensesMainDto findExpenseById(@PathVariable long id);

    @RequestMapping(method = RequestMethod.PUT, value = "/payment/admin/expenses/approve/{empId}", consumes = "application/json")
    public String expensesApproveByAdmin(@PathVariable long empId);

    @RequestMapping(method = RequestMethod.PUT, value = "/payment/admin/salary/credit/{empId}", consumes = "application/json")
    public String salaryCredit(@PathVariable long empId);
}
