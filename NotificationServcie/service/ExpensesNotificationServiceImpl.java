package com.employeeManagement.notificationService.service.impl;

import com.employeeManagement.notificationService.dto.ExpensesNotificationDto;
import com.employeeManagement.notificationService.entity.ExpensesNotification;
import com.employeeManagement.notificationService.exception.ResourceNotFoundException;
import com.employeeManagement.notificationService.repository.ExpensesNotificationRepository;
import com.employeeManagement.notificationService.service.ExpensesNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesNotificationServiceImpl implements ExpensesNotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ExpensesNotificationRepository expensesNotificationRepository;
    @Override
    public ExpensesNotificationDto mapToExpensesNotificationDto(ExpensesNotification expensesNotification) {
        ExpensesNotificationDto expensesNotificationDto = modelMapper.map(expensesNotification,ExpensesNotificationDto.class);
        return expensesNotificationDto;
    }

    @Override
    public ExpensesNotification mapToExpensesNotification(ExpensesNotificationDto expensesNotificationDto) {
        ExpensesNotification expensesNotification = modelMapper.map(expensesNotificationDto,ExpensesNotification.class);
        return expensesNotification;
    }

    @Override
    public List<ExpensesNotificationDto> findAll() {
        return expensesNotificationRepository.findAll().stream().map(this::mapToExpensesNotificationDto).collect(Collectors.toList());
    }

    @Override
    public ExpensesNotificationDto findById(long enId) {
        Optional<ExpensesNotification> result = expensesNotificationRepository.findById(enId);
        ExpensesNotification expensesNotification= null;
        if(result.isPresent()){
            expensesNotification = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToExpensesNotificationDto(expensesNotification);
    }

    @Override
    public String deleteById(long enId) {
        String message;
        Optional<ExpensesNotification> result = expensesNotificationRepository.findById(enId);
        if(result.isPresent()){
            expensesNotificationRepository.deleteById(enId);
            message ="Delete Notification Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public ExpensesNotificationDto save(ExpensesNotificationDto expensesNotificationDto) {
        ExpensesNotification expensesNotification = expensesNotificationRepository.save(mapToExpensesNotification(expensesNotificationDto));
        ExpensesNotificationDto expensesNotificationDto1 = mapToExpensesNotificationDto(expensesNotification);
        return expensesNotificationDto1;
    }

    @Override
    public List<ExpensesNotificationDto> findByEmpId(long empId) {
      return expensesNotificationRepository.findByEmpId(empId).stream().map(this::mapToExpensesNotificationDto).collect(Collectors.toList());
    }
}
