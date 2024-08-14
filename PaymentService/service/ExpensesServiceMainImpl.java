package com.employeeManagement.paymentsService.service.impl;

import com.employeeManagement.paymentsService.dto.ExpensesMainDto;
import com.employeeManagement.paymentsService.entity.ExpensesMain;
import com.employeeManagement.paymentsService.exception.ExpensesNotFoundException;
import com.employeeManagement.paymentsService.exception.ResourceNotFoundException;
import com.employeeManagement.paymentsService.repository.ExpensesMainRepository;
import com.employeeManagement.paymentsService.service.ExpensesMainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesMainServiceImpl implements ExpensesMainService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExpensesMainRepository expensesMainRepository;
    @Override
    public ExpensesMainDto mapToExpensesMainDto(ExpensesMain expensesMain) {
        ExpensesMainDto expensesMainDto = modelMapper.map(expensesMain,ExpensesMainDto.class);
        return expensesMainDto;
    }

    @Override
    public ExpensesMain mapToExpensesMain(ExpensesMainDto expensesMainDto) {
        ExpensesMain expensesMain = modelMapper.map(expensesMainDto,ExpensesMain.class);
        return expensesMain;
    }

    @Override
    public List<ExpensesMainDto> findAll() {
        return expensesMainRepository.findAll().stream().map(this::mapToExpensesMainDto).collect(Collectors.toList());
    }

    @Override
    public ExpensesMainDto findById(long id) {
        Optional<ExpensesMain> result = expensesMainRepository.findById(id);
        ExpensesMain expensesMain = null;
        if(result.isPresent()){
            expensesMain = result.get();
        }
        else {
            throw new ExpensesNotFoundException();
        }
        return mapToExpensesMainDto(expensesMain);
    }

    @Override
    public String deleteById(long id) {
        String message;
        Optional<ExpensesMain> result = expensesMainRepository.findById(id);
        if(result.isPresent()){
            expensesMainRepository.deleteById(id);
            message ="Delete Employee Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public ExpensesMainDto save(ExpensesMainDto expensesMainDto) {
        ExpensesMain expensesMain = expensesMainRepository.save(mapToExpensesMain(expensesMainDto));
        ExpensesMainDto expensesMainDto1 = mapToExpensesMainDto(expensesMain);
        return expensesMainDto1;
    }

    @Override
    public ExpensesMainDto update(ExpensesMainDto employeeDto) {
        Optional<ExpensesMain> result = expensesMainRepository.findById(employeeDto.getEid());
        ExpensesMain expensesMain = null;
        if(result.isPresent()){
            expensesMain = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        ExpensesMain  expensesMain1 = expensesMainRepository.save(mapToExpensesMain(employeeDto));
        ExpensesMainDto expensesMainDto = mapToExpensesMainDto(expensesMain1);
        return expensesMainDto;
    }

    @Override
    public String amountUpdate(long empId, long amount,String status) {
        expensesMainRepository.amountUpdate(empId,amount,status);
        return "Successfully Update";
    }

    @Override
    public ExpensesMainDto findByEmpId(long empId) {
        ExpensesMain expensesMain = expensesMainRepository.findByEmpId(empId);
        return mapToExpensesMainDto(expensesMain);
    }
}
