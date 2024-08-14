package com.employeeManagement.employeeService.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeDto {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_generator")
    @SequenceGenerator(name = "employee_generator",initialValue = 877501,allocationSize = 1,sequenceName ="employee_seq")
    private long empId;

    @NotEmpty(message = "employee name should be present")
    @Size(min = 3,message = "employee name should be more than three letters")
    private  String empName;
    @Column(unique = true)
    @NotEmpty(message = "Email should be present")
    @Email(message = "Should be email format")
    private  String email;
    @NotEmpty(message = "Location should be present")
    private String workLocation;
    @NotEmpty(message = "Role should be present")
    @Size(min = 1,message = "Role name should be more than three letters")
    private String role;
    @NotEmpty(message = "Reporting Manager should be present")
    @Size(min = 3,message = "Manager name should be more than three letters")
    private String reportingManager;
    @NotNull(message = "Manager Id should be present")
    private long managerId;
    @NotEmpty(message = "HR should be present")
    @Size(min = 3,message = "HR name should be more than three letters")
    private String hrName;
    @NotNull(message = "HR id should be present")
    private long hrId;
    @NotEmpty(message = "Status should be present")
    private String status;

    //constructor default

    public EmployeeDto() {
    }
    //parameter constructor


    public EmployeeDto(long empId, String empName, String email, String workLocation, String role, String reportingManager, @NotNull(message = "Manager Id should be present") long managerId, String hrName, @NotNull(message = "HR id should be present") long hrId, String status) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.workLocation = workLocation;
        this.role = role;
        this.reportingManager = reportingManager;
        this.managerId = managerId;
        this.hrName = hrName;
        this.hrId = hrId;
        this.status = status;
    }

    public EmployeeDto(String empName, String email, String workLocation, String role, String reportingManager, @NotNull(message = "Manager Id should be present") long managerId, String hrName, @NotNull(message = "HR id should be present") long hrId, String status) {
        this.empName = empName;
        this.email = email;
        this.workLocation = workLocation;
        this.role = role;
        this.reportingManager = reportingManager;
        this.managerId = managerId;
        this.hrName = hrName;
        this.hrId = hrId;
        this.status = status;
    }

    //parameter constructor without empId


    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public long getHrId() {
        return hrId;
    }

    public void setHrId(long hrId) {
        this.hrId = hrId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
