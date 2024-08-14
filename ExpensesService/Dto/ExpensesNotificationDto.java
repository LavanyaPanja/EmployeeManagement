package com.employeeManagement.expensesService.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ExpensesNotificationDto {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "expenses_notification_generator")
    @SequenceGenerator(name = "expenses_notification_generator",initialValue = 700,allocationSize = 1,sequenceName ="expenses_notification_seq")
    private long enId;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "Description should be present")
    private String description;

    public ExpensesNotificationDto() {

    }

    public ExpensesNotificationDto(long empId, String description) {
        this.empId = empId;
        this.description = description;
    }

    public long getEnId() {
        return enId;
    }

    public void setEnId(long enId) {
        this.enId = enId;
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
