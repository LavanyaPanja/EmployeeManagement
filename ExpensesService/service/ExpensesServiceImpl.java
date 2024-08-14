package com.employeeManagement.expensesService.service.impl;

import com.employeeManagement.expensesService.dto.*;
import com.employeeManagement.expensesService.entity.Expenses;
import com.employeeManagement.expensesService.exception.ResourceNotFoundException;
import com.employeeManagement.expensesService.openfeign.ApiClientExpensesNotification;
import com.employeeManagement.expensesService.openfeign.ApiClientPayment;
import com.employeeManagement.expensesService.repository.ExpensesRepository;
import com.employeeManagement.expensesService.service.ExpensesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private ApiClientPayment apiClientPayment;
    @Autowired
    private ApiClientExpensesNotification apiClientExpensesNotification;
    @Override
    public ExpensesDto mapToExpensesDto(Expenses expenses) {
        ExpensesDto expensesDto = modelMapper.map(expenses,ExpensesDto.class);
        return expensesDto;
    }

    @Override
    public Expenses mapToExpenses(ExpensesDto expensesDto) {
        Expenses expenses = modelMapper.map(expensesDto,Expenses.class);
        return expenses;
    }

    @Override
    public List<ExpensesDto> findAll() {
        return expensesRepository.findAll().stream().map(this::mapToExpensesDto).collect(Collectors.toList());
    }

    @Override
    public ExpensesDto findById(long expensesId) {
        Optional<Expenses> result = expensesRepository.findById(expensesId);
        Expenses expenses= null;
        if(result.isPresent()){
            expenses = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToExpensesDto(expenses);
    }

    @Override
    public String deleteById(long expensesId) {
        String message;
        Optional<Expenses> result = expensesRepository.findById(expensesId);
        if(result.isPresent()){
            expensesRepository.deleteById(expensesId);
            message ="Delete Expenses Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public ExpensesDto save(ExpensesDto expensesDto) {
        Expenses expenses = expensesRepository.save(mapToExpenses(expensesDto));
        ExpensesDto employeeDto1 = mapToExpensesDto(expenses);
        return employeeDto1;
    }

    @Override
    public ExpensesDto update(ExpensesDto expensesDto) {
        Optional<Expenses> result = expensesRepository.findById(expensesDto.getExpenseId());
        Expenses expenses = null;
        if(result.isPresent()){
            expenses = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Expenses expenses1 = expensesRepository.save(mapToExpenses(expensesDto));
        ExpensesDto expensesDto1 = mapToExpensesDto(expenses1);
        return expensesDto1;
    }

    @Override
    public String approve(RequestDto requestDto) {
        String message;
        Optional<Expenses> result = expensesRepository.findById(requestDto.getExpenseId());
        if(result.isPresent()){
            String status = "Approved";
            expensesRepository.updateStatus(requestDto.getExpenseId(),requestDto.getEmployeeId(),requestDto.getHrId(),status);
            message ="Approved";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public String reject(RequestDto requestDto) {
        String message;
        Optional<Expenses> result = expensesRepository.findById(requestDto.getExpenseId());
        if(result.isPresent()){
            String status = "Rejected";
            expensesRepository.updateStatus(requestDto.getExpenseId(),requestDto.getEmployeeId(),requestDto.getHrId(),status);
            message ="Rejected";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public List<ExpensesDto> findByEmpId(long employeeId) {
        return expensesRepository.findByEmpId(employeeId).stream().map(this::mapToExpensesDto).collect(Collectors.toList());
    }

    @Override
    public List<ExpensesDto> findByEmpIdWithStatus(long employeeId, String status) {
        return expensesRepository.findByEmpIdWithStatus(employeeId,status).stream().map(this::mapToExpensesDto).collect(Collectors.toList());
    }

    @Override
    public List<ExpensesDto> findByManagerIdWithStatus(long hrId, String status) {
        return expensesRepository.findByManagerIdWithStatus(hrId,status).stream().map(this::mapToExpensesDto).collect(Collectors.toList());
    }

    @Override
    public String updateAmount(UpdatePayload updatePayload) {
        return apiClientPayment.amountUpdate(updatePayload);
    }

    @Override
    public List<ExpensesNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId) {
        return apiClientExpensesNotification.findByIdEmployeeIdOpenFeign(employeeId);
    }

    @Override
    public String expensesNotificationAdd(ExpensesNotificationDto expensesNotificationDto) {
        ExpensesNotificationDto expensesNotificationDto1 = apiClientExpensesNotification.expensesNotificationAdd(expensesNotificationDto);
        return "Expenses Notification Add Successfully";
    }


}
