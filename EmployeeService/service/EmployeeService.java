package com.employeeManagement.employeeService.service;

import com.employeeManagement.employeeService.dto.*;
import com.employeeManagement.employeeService.entity.Employee;


import java.util.List;

public interface EmployeeService {
    //mapper methods
    public EmployeeDto mapToEmployeeDto(Employee employee);
    public Employee mapToEmployee(EmployeeDto employeeDto);
    public List<EmployeeDto> findAll();
    public EmployeeDto findById(long employeeId);
    public String deleteById(long employeeId);
    public EmployeeDto save(EmployeeDto employeeDto);
    public EmployeeDto update(EmployeeDto employeeDto);
    public List<EmployeeDto> duplicateCheckService(String title);

    public List<EmployeeDto> employeeListByHr(long hrId);
    List<EmployeeDto> employeeListByManagerId(long managerId);
    //expenses-service
    public ExpensesDto findByIdForExpensesOpenFeign(long id);
    public List<ExpensesDto> findByEmpIdForExpensesOpenFeign(long employeeId);

    public List<ExpensesDto> findByIdEmployeeIdWithStatusOpenFeign(long employeeId);

    public ExpensesDto createExpensesByEmployee(ExpensesDto expensesDto);
    public String deleteExpensesById(long expensesId);

    //leave-service
    public LeavesDto findByIdForLeavesOpenFeign(long leaveId);
    public List<LeavesDto> findByEmpIdForLeavesOpenFeign(long employeeId);

    public List<LeavesDto> findByIdLeaveEmployeeIdWithStatusOpenFeign(long employeeId);

    public LeavesDto createLeavesByEmployee(LeavesDto leavesDto);
    public LeavesDto editLeavesOpenFeign(LeavesDto leavesDto);
    public String deleteLeavesById(long id);
    public  TotalLeavesDto totalLeavesId( long employeeId);

    public SalaryDto findByEmpIdForSalaryOpenFeign(long employeeId);

    public List<LeaveNotificationDto> leavesNotification(long empId);
    public List<ExpensesNotificationDto> expensesNotification(long empId);
    public List<SalaryNotificationDto> salaryNotification(long empId);
}
