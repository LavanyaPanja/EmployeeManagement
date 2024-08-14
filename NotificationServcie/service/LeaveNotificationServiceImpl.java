package com.employeeManagement.notificationService.service.impl;

import com.employeeManagement.notificationService.dto.LeaveNotificationDto;
import com.employeeManagement.notificationService.entity.LeaveNotification;
import com.employeeManagement.notificationService.exception.ResourceNotFoundException;
import com.employeeManagement.notificationService.repository.LeaveNotificationRepository;
import com.employeeManagement.notificationService.service.LeaveNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveNotificationServiceImpl implements LeaveNotificationService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LeaveNotificationRepository leaveNotificationRepository;
    @Override
    public LeaveNotificationDto mapToLeaveNotificationDto(LeaveNotification leaveNotification) {
        LeaveNotificationDto leaveNotificationDto = modelMapper.map(leaveNotification,LeaveNotificationDto.class);
        return leaveNotificationDto;
    }

    @Override
    public LeaveNotification mapToLeaveNotification(LeaveNotificationDto leaveNotificationDto) {
        LeaveNotification leaveNotification = modelMapper.map(leaveNotificationDto,LeaveNotification.class);
        return leaveNotification;
    }

    @Override
    public List<LeaveNotificationDto> findAll() {
        return leaveNotificationRepository.findAll().stream().map(this::mapToLeaveNotificationDto).collect(Collectors.toList());
    }

    @Override
    public LeaveNotificationDto findById(long NId) {
        Optional<LeaveNotification> result = leaveNotificationRepository.findById(NId);
        LeaveNotification leaveNotification= null;
        if(result.isPresent()){
            leaveNotification = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToLeaveNotificationDto(leaveNotification);
    }

    @Override
    public String deleteById(long Nid) {
        String message;
        Optional<LeaveNotification> result = leaveNotificationRepository.findById(Nid);
        if(result.isPresent()){
            leaveNotificationRepository.deleteById(Nid);
            message ="Delete Notification Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public LeaveNotificationDto save(LeaveNotificationDto leaveNotificationDto) {
        LeaveNotification leaveNotification = leaveNotificationRepository.save(mapToLeaveNotification(leaveNotificationDto));
        LeaveNotificationDto leaveNotificationDto1 = mapToLeaveNotificationDto(leaveNotification);
        return leaveNotificationDto1;
    }

    @Override
    public List<LeaveNotificationDto> findByEmpId(long empId) {
        return leaveNotificationRepository.findByEmpId(empId).stream().map(this::mapToLeaveNotificationDto).collect(Collectors.toList());
    }
}
