package com.employeeManagement.employeeService.service.impl;

import com.employeeManagement.employeeService.dto.*;
import com.employeeManagement.employeeService.entity.Employee;
import com.employeeManagement.employeeService.exception.ResourceNotFoundException;
import com.employeeManagement.employeeService.openfeign.ApiClientExpenses;
import com.employeeManagement.employeeService.openfeign.ApiClientLeave;
import com.employeeManagement.employeeService.openfeign.ApiClientNotification;
import com.employeeManagement.employeeService.openfeign.ApiClientPayment;
import com.employeeManagement.employeeService.repository.EmployeeRepository;
import com.employeeManagement.employeeService.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApiClientExpenses apiClientExpenses;
    @Autowired
    private ApiClientLeave apiClientLeave;
    @Autowired
    private ApiClientPayment apiClientPayment;
    @Autowired
    private ApiClientNotification apiClientNotification;
    @Override
    public EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee,EmployeeDto.class);
        return employeeDto;
    }

    @Override
    public Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto,Employee.class);
        return employee;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(long employeeId) {
        Optional<Employee> result = employeeRepository.findById(employeeId);
        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToEmployeeDto(employee);
    }

    @Override
    public String deleteById(long employeeId) {
        String message;
        Optional<Employee> result = employeeRepository.findById(employeeId);
        if(result.isPresent()){
            employeeRepository.deleteById(employeeId);
            message ="Delete Employee Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.save(mapToEmployee(employeeDto));
        EmployeeDto employeeDto1 = mapToEmployeeDto(employee);
        return employeeDto1;
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        Optional<Employee> result = employeeRepository.findById(employeeDto.getEmpId());
        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Employee employee1 = employeeRepository.save(mapToEmployee(employeeDto));
        EmployeeDto employeeDto1 = mapToEmployeeDto(employee1);
        return employeeDto1;
    }

    @Override
    public List<EmployeeDto> duplicateCheckService(String email) {
        return employeeRepository.duplicateCheck(email).stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> employeeListByHr(long hrId) {
        return employeeRepository.employeeListByHrID(hrId).stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> employeeListByManagerId(long managerId) {
        return employeeRepository.employeeListByManagerId(managerId).stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }
    //expenses-service

    @Override
    public ExpensesDto findByIdForExpensesOpenFeign(long id) {
        return apiClientExpenses.findByIdForExpensesOpenFeign(id);
    }

    @Override
    public List<ExpensesDto> findByEmpIdForExpensesOpenFeign(long employeeId) {
        return apiClientExpenses.findByIdEmployeeIdOpenFeign(employeeId);
    }

    @Override
    public List<ExpensesDto> findByIdEmployeeIdWithStatusOpenFeign(long employeeId) {
        return apiClientExpenses.findByIdEmployeeIdWithStatusOpenFeign(employeeId);
    }

    @Override
    public ExpensesDto createExpensesByEmployee(ExpensesDto expensesDto) {
        ExpensesDto expensesDto1 = apiClientExpenses.createExpensesByEmployee(expensesDto);
        return expensesDto;
    }

    @Override
    public String deleteExpensesById(long expensesId) {
        return apiClientExpenses.deleteExpensesById(expensesId);
    }
    //leave-service
    @Override
    public LeavesDto findByIdForLeavesOpenFeign(long leaveId) {
        return apiClientLeave.findByIdForLeavesOpenFeign(leaveId);
    }

    @Override
    public List<LeavesDto> findByEmpIdForLeavesOpenFeign(long employeeId) {
        return apiClientLeave.findByIdEmployeeIdOpenFeign(employeeId);
    }

    @Override
    public List<LeavesDto> findByIdLeaveEmployeeIdWithStatusOpenFeign(long employeeId) {
        return apiClientLeave.findByIdEmployeeIdWithStatusOpenFeign(employeeId);
    }

    @Override
    public LeavesDto createLeavesByEmployee(LeavesDto leavesDto) {
        LeavesDto leavesDto1 = apiClientLeave.createLeavesByEmployee(leavesDto);
        return leavesDto1;
    }

    @Override
    public LeavesDto editLeavesOpenFeign(LeavesDto leavesDto) {
        return apiClientLeave.editLeavesOpenFeign(leavesDto);
    }

    @Override
    public String deleteLeavesById(long id) {
        return apiClientLeave.deleteLeavesById(id);
    }

    @Override
    public TotalLeavesDto totalLeavesId(long employeeId) {
        return apiClientLeave.totalLeavesId(employeeId);
    }

    @Override
    public SalaryDto findByEmpIdForSalaryOpenFeign(long employeeId) {
        return apiClientPayment.findByIdForSalaryOpenFeign(employeeId);
    }

    @Override
    public List<LeaveNotificationDto> leavesNotification(long empId) {
        return apiClientNotification.leavesNotification(empId);
    }

    @Override
    public List<ExpensesNotificationDto> expensesNotification(long empId) {
        return apiClientNotification.expensesNotification(empId);
    }

    @Override
    public List<SalaryNotificationDto> salaryNotification(long empId) {
        return apiClientNotification.salaryNotification(empId);
    }
}
