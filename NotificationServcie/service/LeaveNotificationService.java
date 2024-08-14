package com.employeeManagement.notificationService.service;

import com.employeeManagement.notificationService.dto.LeaveNotificationDto;
import com.employeeManagement.notificationService.entity.LeaveNotification;

import java.util.List;

public interface LeaveNotificationService {
    public LeaveNotificationDto mapToLeaveNotificationDto(LeaveNotification leaveNotification);
    public LeaveNotification mapToLeaveNotification(LeaveNotificationDto leaveNotificationDto);

    public List<LeaveNotificationDto> findAll();
    public LeaveNotificationDto findById(long NId);
    public String deleteById(long Nid);
    public LeaveNotificationDto save(LeaveNotificationDto leaveNotificationDto);

    public List<LeaveNotificationDto> findByEmpId(long empId);
}
