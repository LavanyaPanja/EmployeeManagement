package com.employeeManagement.securityService.dto;

import jakarta.validation.constraints.NotEmpty;

public class ExpensesNotificationDto {
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "expenses_notification_generator")
    @SequenceGenerator(name = "expenses_notification_generator",initialValue = 700,allocationSize = 1,sequenceName ="expenses_notification_seq")
    private long enId;
    @NotNull(message = "Employee Id should be present")
    private long empId;*/
    @NotEmpty(message = "Description should be present")
    private String description;

    public ExpensesNotificationDto() {

    }

    public ExpensesNotificationDto(String description) {
        //this.empId = empId;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
