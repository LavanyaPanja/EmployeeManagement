package com.employeeManagement.notificationService.service;


import com.employeeManagement.notificationService.dto.ExpensesNotificationDto;

import com.employeeManagement.notificationService.entity.ExpensesNotification;

import java.util.List;

public interface ExpensesNotificationService {
    public ExpensesNotificationDto mapToExpensesNotificationDto(ExpensesNotification expensesNotification);
    public ExpensesNotification mapToExpensesNotification(ExpensesNotificationDto expensesNotificationDto);
    public List<ExpensesNotificationDto> findAll();
    public ExpensesNotificationDto findById(long enId);
    public String deleteById(long enId);
    public ExpensesNotificationDto save(ExpensesNotificationDto expensesNotificationDto);

    public List<ExpensesNotificationDto> findByEmpId(long empId);
}
