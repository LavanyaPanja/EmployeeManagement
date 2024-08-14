package com.employeeManagement.notificationService.service;


import com.employeeManagement.notificationService.dto.SalaryNotificationDto;
import com.employeeManagement.notificationService.entity.SalaryNotification;

import java.util.List;

public interface SalaryNotificationService {
    public SalaryNotificationDto mapToSalaryNotificationDto(SalaryNotification salaryNotification);
    public SalaryNotification mapToSalaryNotification(SalaryNotificationDto salaryNotificationDto);
    public List<SalaryNotificationDto> findAll();
    public SalaryNotificationDto findById(long enId);
    public String deleteById(long enId);
    public SalaryNotificationDto save(SalaryNotificationDto expensesNotificationDto);

    public List<SalaryNotificationDto> findByEmpId(long empId);
}
