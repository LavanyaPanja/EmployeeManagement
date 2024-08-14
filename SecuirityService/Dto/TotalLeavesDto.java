package com.employeeManagement.securityService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TotalLeavesDto {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "leaves_generator")
    @SequenceGenerator(name = "leaves_generator",initialValue = 501,allocationSize = 1,sequenceName ="leaves_seq")
    private long Id;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "employee name should be present")
    private String empName;

    @Column(name = "earnedLeave")
    @NotNull(message = "earned flied should be present")
    private int earned;
    @Column(name = "sickLeave")
    @NotNull(message = "sickLeave flied should be present")
    private int sick;

    @NotNull(message = "Manager id should be present")
    private long managerId;
    @NotEmpty(message = "Manager name should be present")
    private String reportingManager;

    public TotalLeavesDto() {
    }

    public TotalLeavesDto(long empId, String empName, int earned, int sick, long managerId, String reportingManager) {
        this.empId = empId;
        this.empName = empName;
        this.earned = earned;
        this.sick = sick;
        this.managerId = managerId;
        this.reportingManager = reportingManager;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

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

    public int getEarned() {
        return earned;
    }

    public void setEarned(int earned) {
        this.earned = earned;
    }

    public int getSick() {
        return sick;
    }

    public void setSick(int sick) {
        this.sick = sick;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }
}
