package com.employeeManagement.securityService.service;

import com.employeeManagement.securityService.dto.ExpensesMainDto;
import com.employeeManagement.securityService.dto.SalaryDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface AdminSecurityService {
    public List<SalaryDto> findAllSalaryAccounts();
    public String deleteSalaryAccount(long id);
    public SalaryDto createSalaryAccount(SalaryDto salaryDto);

    public SalaryDto updateSalaryAccount(SalaryDto salaryDto);

    public List<ExpensesMainDto> findAllExpensesAccounts();
    public String deleteExpensesAccount(long id);
    public ExpensesMainDto createExpensesAccount(ExpensesMainDto expensesMainDto);
    public ExpensesMainDto updateExpensesAccount(ExpensesMainDto expensesMainDto);
    public SalaryDto findByAccountId( long id);
    public ExpensesMainDto findExpenseById( long id);
    public String expensesApproveByAdmin(long empId);
    public String salaryCredit(long empId);
}
