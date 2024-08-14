package com.employeeManagement.expensesService.service;

import com.employeeManagement.expensesService.dto.*;
import com.employeeManagement.expensesService.entity.Expenses;


import java.util.List;

public interface ExpensesService {
    public ExpensesDto mapToExpensesDto(Expenses expenses);
    public Expenses mapToExpenses(ExpensesDto expensesDto);
    public List<ExpensesDto> findAll();
    public ExpensesDto findById(long expensesId);
    public String deleteById(long expensesId);
    public ExpensesDto save(ExpensesDto expensesDto);
    public ExpensesDto update(ExpensesDto expensesDto);

    public String approve(RequestDto requestDto);
    public String reject(RequestDto requestDto);

    public List<ExpensesDto> findByEmpId(long employeeId);
    public List<ExpensesDto> findByEmpIdWithStatus(long employeeId,String status);
    public List<ExpensesDto> findByManagerIdWithStatus(long hrId,String status);
    public String updateAmount(UpdatePayload updatePayload);
    public List<ExpensesNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId);
    public String expensesNotificationAdd(ExpensesNotificationDto expensesNotificationDto);

}
