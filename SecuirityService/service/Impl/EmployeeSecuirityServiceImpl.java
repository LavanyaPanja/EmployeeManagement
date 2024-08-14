package com.employeeManagement.securityService.service.impl;

import com.employeeManagement.securityService.dto.*;
import com.employeeManagement.securityService.openfeign.ApiClientEmployee;
import com.employeeManagement.securityService.service.EmployeeSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSecurityServiceImpl implements EmployeeSecurityService {

    @Autowired
    private ApiClientEmployee apiClientEmployee;
    @Override
    public EmployeeDto findByIdForEmployeeOpenFeign(long id) {
        return apiClientEmployee.findByIdForEmployeeOpenFeign(id);
    }

    @Override
    public EmployeeDto createEmployeeByHR(EmployeeDto employeeDto) {
        return apiClientEmployee.createEmployeeByHR(employeeDto);
    }

    @Override
    public List<EmployeeDto> allListEmployeeByHr(long hrId) {
        return apiClientEmployee.allListEmployeeByHr(hrId);
    }

    @Override
    public List<EmployeeDto> allListEmployeeByManager(long managerId) {
        return apiClientEmployee.allListEmployeeByManager(managerId);
    }

    @Override
    public String deleteEmployeeById(long empId) {
        return apiClientEmployee.deleteEmployeeById(empId);
    }

    @Override
    public ExpensesDto findByIdForExpensesOpenFeign(long id) {
        return apiClientEmployee.findByIdForExpensesOpenFeign(id);
    }

    @Override
    public List<ExpensesDto> findByIdExpensesIdOpenFeign(long employeeId) {
        return apiClientEmployee.findByIdExpensesIdOpenFeign(employeeId);
    }

    @Override
    public List<ExpensesDto> findByIdExpensesIdWithStatusOpenFeign(long employeeId) {
        return apiClientEmployee.findByIdExpensesIdWithStatusOpenFeign(employeeId);
    }


    @Override
    public String createExpensesByEmployee(ExpensesDto expensesDto) {
        return apiClientEmployee.createExpensesByEmployee(expensesDto);
    }

    @Override
    public String deleteExpensesById(long id) {
        return apiClientEmployee.deleteExpensesById(id);
    }

    // leaves service
    @Override
    public LeavesDto findByIdForLeavesOpenFeign(long id) {
        return apiClientEmployee.findByIdForLeavesOpenFeign(id);
    }

    @Override
    public List<LeavesDto> findByIdLeavesIdOpenFeign(long employeeId) {
        return apiClientEmployee.findByIdLeavesIdOpenFeign(employeeId);
    }

    @Override
    public List<LeavesDto> findByIdLeavesIdWithStatusOpenFeign(long employeeId) {
        return apiClientEmployee.findByIdLeavesIdWithStatusOpenFeign(employeeId);
    }

    @Override
    public TotalLeavesDto totalLeavesId(long employeeId) {
        return apiClientEmployee.totalLeavesId(employeeId);
    }


    @Override
    public String createLeavesByEmployee(LeavesDto leavesDto) {
        return apiClientEmployee.createLeavesByEmployee(leavesDto);
    }

    @Override
    public String editLeavesOpenFeign(LeavesDto leavesDto) {
        return apiClientEmployee.editLeavesOpenFeign(leavesDto);
    }

    @Override
    public String deleteLeavesById(long id) {
        return apiClientEmployee.deleteLeavesById(id);
    }

    @Override
    public SalaryDto findByIdForSalaryOpenFeign(long empId) {
        return apiClientEmployee.findByIdForSalaryOpenFeign(empId);
    }

    @Override
    public List<LeaveNotificationDto> leavesNotification(long empId) {
        return apiClientEmployee.leavesNotification(empId);
    }

    @Override
    public List<ExpensesNotificationDto> expensesNotification(long empId) {
        return apiClientEmployee.expensesNotification(empId);
    }

    @Override
    public List<SalaryNotificationDto> salaryNotification(long empId) {
        return apiClientEmployee.salaryNotification(empId);
    }
}
