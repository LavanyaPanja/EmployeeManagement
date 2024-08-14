package com.employeeManagement.employeeService.dto;


import jakarta.validation.constraints.NotEmpty;


public class SalaryNotificationDto {
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salary_notification_generator")
    @SequenceGenerator(name = "salary_notification_generator",initialValue = 900,allocationSize = 1,sequenceName ="salary_notification_seq")
    private long snId;
    @NotNull(message = "Employee Id should be present")
    private long empId;*/
    @NotEmpty(message = "Description should be present")
    private String description;

    public SalaryNotificationDto() {
    }

    public SalaryNotificationDto(String description) {
       // this.empId = empId;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
