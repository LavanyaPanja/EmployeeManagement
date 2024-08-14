package com.employeeManagement.leavesService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "leave_request_generator")
    @SequenceGenerator(name = "leave_request_generator",initialValue = 44501,allocationSize = 1,sequenceName ="leave_request_seq")
    private long leaveId;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "employee name should be present")
    private String empName;
    @JsonFormat
    private LocalDate toDate;
    @JsonFormat
    private LocalDate endDate;
    @NotNull(message = "Number Of Days Id should be present")
    private int noDays;
    @NotEmpty(message = "Status should be present")
    private String status;
    @NotNull(message = "Manager id should be present")
    private long managerId;
    @NotEmpty(message = "Manager name should be present")
    private String reportingManager;
    @NotEmpty(message = "category should be present")
    @Size(min = 3,message = "category should be more than three letters")
    private String category;

    public Leaves() {
    }

    public Leaves(long empId, String empName, LocalDate toDate, LocalDate endDate,int noDays, String status,long managerId, String reportingManager, String category) {
        this.empId = empId;
        this.empName = empName;
        this.toDate = toDate;
        this.endDate = endDate;
        this.noDays = noDays;
        this.status = status;
        this.managerId = managerId;
        this.reportingManager = reportingManager;
        this.category = category;
    }

    public long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(long leaveId) {
        this.leaveId = leaveId;
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

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNoDays() {
        return noDays;
    }

    public void setNoDays(int noDays) {
        this.noDays = noDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
