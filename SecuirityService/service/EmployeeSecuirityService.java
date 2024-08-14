package com.employeeManagement.securityService.service;

import com.employeeManagement.securityService.dto.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface EmployeeSecurityService {
    public EmployeeDto findByIdForEmployeeOpenFeign(long id);

    public EmployeeDto createEmployeeByHR(EmployeeDto employeeDto);

    public List<EmployeeDto> allListEmployeeByHr(long hrId);
    public List<EmployeeDto> allListEmployeeByManager(long managerId);
    public String deleteEmployeeById( long id);
    public ExpensesDto findByIdForExpensesOpenFeign( long id);
    public List<ExpensesDto> findByIdExpensesIdOpenFeign( long employeeId);
    public List<ExpensesDto> findByIdExpensesIdWithStatusOpenFeign( long employeeId);
    public String createExpensesByEmployee(ExpensesDto expensesDto);
    public String deleteExpensesById( long id);

    public LeavesDto findByIdForLeavesOpenFeign( long id);
    public List<LeavesDto> findByIdLeavesIdOpenFeign( long employeeId);

    public List<LeavesDto> findByIdLeavesIdWithStatusOpenFeign( long employeeId);
    public  TotalLeavesDto totalLeavesId( long employeeId);

    public String createLeavesByEmployee(LeavesDto leavesDto);
    public String editLeavesOpenFeign(LeavesDto leavesDto);
    public String deleteLeavesById( long id);
    public SalaryDto findByIdForSalaryOpenFeign(long empId);
    public List<LeaveNotificationDto> leavesNotification(long empId);
    public List<ExpensesNotificationDto> expensesNotification(long empId);
    public List<SalaryNotificationDto> salaryNotification(long empId);

}
