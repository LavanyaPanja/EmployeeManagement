package com.employeeManagement.notificationService.service.impl;


import com.employeeManagement.notificationService.dto.SalaryNotificationDto;
import com.employeeManagement.notificationService.entity.SalaryNotification;
import com.employeeManagement.notificationService.exception.ResourceNotFoundException;
import com.employeeManagement.notificationService.repository.SalaryNotificationRepository;
import com.employeeManagement.notificationService.service.SalaryNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryNotificationServiceImpl implements SalaryNotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalaryNotificationRepository salaryNotificationRepository;
    @Override
    public SalaryNotificationDto mapToSalaryNotificationDto(SalaryNotification salaryNotification) {
        SalaryNotificationDto salaryNotificationDto1 = modelMapper.map(salaryNotification,SalaryNotificationDto.class);
        return salaryNotificationDto1;
    }

    @Override
    public SalaryNotification mapToSalaryNotification(SalaryNotificationDto salaryNotificationDto) {
        SalaryNotification salaryNotification = modelMapper.map(salaryNotificationDto,SalaryNotification.class);
        return salaryNotification;
    }

    @Override
    public List<SalaryNotificationDto> findAll() {
        return salaryNotificationRepository.findAll().stream().map(this::mapToSalaryNotificationDto).collect(Collectors.toList());
    }

    @Override
    public SalaryNotificationDto findById(long enId) {
        Optional<SalaryNotification> result = salaryNotificationRepository.findById(enId);
        SalaryNotification expensesNotification= null;
        if(result.isPresent()){
            expensesNotification = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToSalaryNotificationDto(expensesNotification);
    }

    @Override
    public String deleteById(long enId) {
        String message;
        Optional<SalaryNotification> result = salaryNotificationRepository.findById(enId);
        if(result.isPresent()){
            salaryNotificationRepository.deleteById(enId);
            message ="Delete Notification Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public SalaryNotificationDto save(SalaryNotificationDto salaryNotificationDto) {
        SalaryNotification salaryNotification = salaryNotificationRepository.save(mapToSalaryNotification(salaryNotificationDto));
        SalaryNotificationDto salaryNotificationDto1 = mapToSalaryNotificationDto(salaryNotification);
        return salaryNotificationDto1;
    }

    @Override
    public List<SalaryNotificationDto> findByEmpId(long empId) {
      return salaryNotificationRepository.findByEmpId(empId).stream().map(this::mapToSalaryNotificationDto).collect(Collectors.toList());
    }
}
