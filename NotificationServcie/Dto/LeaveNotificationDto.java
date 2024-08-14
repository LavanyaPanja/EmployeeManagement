package com.employeeManagement.notificationService.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LeaveNotificationDto {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "leave_notification_generator")
    @SequenceGenerator(name = "leave_notification_generator",initialValue = 500,allocationSize = 1,sequenceName ="leave_notification_seq")
    private long NId;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "Description should be present")
    private String description;

    public LeaveNotificationDto() {
    }

    public LeaveNotificationDto(long empId, String description) {
        this.empId = empId;
        this.description = description;
    }

    public long getNId() {
        return NId;
    }

    public void setNId(long NId) {
        this.NId = NId;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
