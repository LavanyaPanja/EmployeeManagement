package com.employeeManagement.paymentsService.service;

import com.employeeManagement.paymentsService.dto.SalaryDto;
import com.employeeManagement.paymentsService.dto.SalaryNotificationDto;
import com.employeeManagement.paymentsService.entity.Salary;

import java.time.LocalDateTime;
import java.util.List;

public interface SalaryService {
    public SalaryDto mapToSalaryDto(Salary salary);
    public Salary mapToSalary(SalaryDto salaryDto);

    public List<SalaryDto> findAll();
    public SalaryDto findById(long salaryId);
    public String deleteById(long salaryId);
    public SalaryDto save(SalaryDto salaryDto);

    public SalaryDto update(SalaryDto employeeDto);

    public List<SalaryDto> duplicateCheckService(long accountNumber);

    public String totalExpensesAmountUpdate(long empId,long amount);

    public SalaryDto findByEmpId(long empId);

    public String totalPaymentDate(long empId, LocalDateTime paymentDate,long totalAmount);

    public List<SalaryNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId);
    public String salaryNotificationAdd(SalaryNotificationDto salaryNotificationDto);
}
