package com.employeeManagement.paymentsService.service;

import com.employeeManagement.paymentsService.dto.ExpensesMainDto;
import com.employeeManagement.paymentsService.entity.ExpensesMain;


import java.util.List;


public interface ExpensesMainService {
    public ExpensesMainDto mapToExpensesMainDto(ExpensesMain expensesMain);
    public ExpensesMain mapToExpensesMain(ExpensesMainDto expensesMainDto);

    public List<ExpensesMainDto> findAll();
    public ExpensesMainDto findById(long id);
    public String deleteById(long id);
    public ExpensesMainDto save(ExpensesMainDto salaryDto);

    public ExpensesMainDto update(ExpensesMainDto employeeDto);
    public String amountUpdate(long empId,long amount,String status);
    ExpensesMainDto findByEmpId(long empId);

   // public List<SalaryDto> duplicateCheckService(long accountNumber);
}
