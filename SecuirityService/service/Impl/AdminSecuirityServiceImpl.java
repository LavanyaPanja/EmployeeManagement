package com.employeeManagement.securityService.service.impl;

import com.employeeManagement.securityService.dto.ExpensesMainDto;
import com.employeeManagement.securityService.dto.SalaryDto;
import com.employeeManagement.securityService.openfeign.ApiClientSalary;
import com.employeeManagement.securityService.service.AdminSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSecurityServiceImpl implements AdminSecurityService {
    @Autowired
    private ApiClientSalary apiClientSalary;
    @Override
    public List<SalaryDto> findAllSalaryAccounts() {
        return apiClientSalary.findAllSalaryAccounts();
    }

    @Override
    public String deleteSalaryAccount(long id) {
        return apiClientSalary.deleteSalaryAccount(id);
    }

    @Override
    public SalaryDto createSalaryAccount(SalaryDto salaryDto) {
        return apiClientSalary.createSalaryAccount(salaryDto);
    }

    @Override
    public SalaryDto updateSalaryAccount(SalaryDto salaryDto) {
        return apiClientSalary.updateSalaryAccount(salaryDto);
    }

    @Override
    public List<ExpensesMainDto> findAllExpensesAccounts() {
        return apiClientSalary.findAllExpensesAccounts();
    }

    @Override
    public String deleteExpensesAccount(long id) {
        return apiClientSalary.deleteExpensesAccount(id);
    }

    @Override
    public ExpensesMainDto createExpensesAccount(ExpensesMainDto expensesMainDto) {
        return apiClientSalary.createExpensesAccount(expensesMainDto);
    }

    @Override
    public ExpensesMainDto updateExpensesAccount(ExpensesMainDto expensesMainDto) {
        return apiClientSalary.updateExpensesAccount(expensesMainDto);
    }

    @Override
    public SalaryDto findByAccountId(long id) {
        return apiClientSalary.findByAccountId(id);
    }

    @Override
    public ExpensesMainDto findExpenseById(long id) {
        return apiClientSalary.findExpenseById(id);
    }

    @Override
    public String expensesApproveByAdmin(long empId) {
        return apiClientSalary.expensesApproveByAdmin(empId);
    }

    @Override
    public String salaryCredit(long empId) {
        return apiClientSalary.salaryCredit(empId);
    }
}
