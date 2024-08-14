package com.employeeManagement.paymentsService.service.impl;

import com.employeeManagement.paymentsService.dto.SalaryDto;
import com.employeeManagement.paymentsService.dto.SalaryNotificationDto;
import com.employeeManagement.paymentsService.entity.Salary;
import com.employeeManagement.paymentsService.exception.ResourceNotFoundException;
import com.employeeManagement.paymentsService.openfeign.ApiClientSalaryNotification;
import com.employeeManagement.paymentsService.repository.SalaryRepository;
import com.employeeManagement.paymentsService.service.SalaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private ApiClientSalaryNotification apiClientSalaryNotification;
    @Override
    public SalaryDto mapToSalaryDto(Salary salary) {
        SalaryDto salaryDto = modelMapper.map(salary,SalaryDto.class);
        return salaryDto;
    }

    @Override
    public Salary mapToSalary(SalaryDto salaryDto) {
        Salary salary = modelMapper.map(salaryDto,Salary.class);
        return salary;
    }

    @Override
    public List<SalaryDto> findAll() {
        return salaryRepository.findAll().stream().map(this::mapToSalaryDto).collect(Collectors.toList());
    }

    @Override
    public SalaryDto findById(long salaryId) {
        Optional<Salary> result = salaryRepository.findById(salaryId);
        Salary salary = null;
        if(result.isPresent()){
            salary = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToSalaryDto(salary);
    }

    @Override
    public String deleteById(long salaryId) {
        String message;
        Optional<Salary> result = salaryRepository.findById(salaryId);
        if(result.isPresent()){
            salaryRepository.deleteById(salaryId);
            message ="Delete Employee Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public SalaryDto save(SalaryDto salaryDto) {
        Salary salary = salaryRepository.save(mapToSalary(salaryDto));
        SalaryDto salaryDto1 = mapToSalaryDto(salary);
        return salaryDto1;
    }

    @Override
    public SalaryDto update(SalaryDto salaryDto) {
        Optional<Salary> result = salaryRepository.findById(salaryDto.getSalaryId());
        Salary salary = null;
        if(result.isPresent()){
            salary = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Salary salary1 = salaryRepository.save(mapToSalary(salaryDto));
        SalaryDto salaryDto1 = mapToSalaryDto(salary1);
        return salaryDto1;
    }

    @Override
    public List<SalaryDto> duplicateCheckService(long accountNumber) {
        return salaryRepository.duplicateCheck(accountNumber).stream().map(this::mapToSalaryDto).collect(Collectors.toList());
    }

    @Override
    public String totalExpensesAmountUpdate(long empId, long amount) {
       salaryRepository.totalAmountUpdate(empId,amount);
       return "Successfully update";
    }

    @Override
    public SalaryDto findByEmpId(long empId) {
        Salary result = salaryRepository.findByEmpId(empId);

        if(result==null){
           throw new ResourceNotFoundException();
        }
        return mapToSalaryDto(result);
    }

    @Override
    public String totalPaymentDate(long empId, LocalDateTime paymentDate,long totalAmount) {
        salaryRepository.totalPaymentDate(empId,paymentDate,totalAmount);
        return "Successfully Date Update";
    }

    @Override
    public List<SalaryNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId) {
        return apiClientSalaryNotification.findByIdEmployeeIdOpenFeign(employeeId);
    }

    @Override
    public String salaryNotificationAdd(SalaryNotificationDto salaryNotificationDto) {
        SalaryNotificationDto salaryNotificationDto1 = apiClientSalaryNotification.salaryNotificationAdd(salaryNotificationDto);
        return "Salary Notification Add Successfully";
    }
}
