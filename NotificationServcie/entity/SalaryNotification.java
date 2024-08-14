package com.employeeManagement.notificationService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salary_notification")
public class SalaryNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salary_notification_generator")
    @SequenceGenerator(name = "salary_notification_generator",initialValue = 900,allocationSize = 1,sequenceName ="salary_notification_seq")
    private long snId;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "Description should be present")
    private String description;

    public SalaryNotification() {
    }

    public SalaryNotification( long empId, String description) {
        this.empId = empId;
        this.description = description;
    }

    public long getSnId() {
        return snId;
    }

    public void setSnId(long snId) {
        this.snId = snId;
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
