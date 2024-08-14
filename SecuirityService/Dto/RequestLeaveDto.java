package com.employeeManagement.securityService.dto;

import jakarta.validation.constraints.NotNull;

public class RequestLeavesDto {
    @NotNull(message = "Should be present value for Leaves Id filed")
    private long leaveId;
    @NotNull(message = "Should be present value for Employee Id filed")
    private long empId;

    @NotNull(message = "Should be present value for Employee Id filed")
    private long managerId;

    private String status;
    private String category;
    public RequestLeavesDto() {
    }

    public RequestLeavesDto(long leaveId, long empId, long managerId) {
        this.leaveId = leaveId;
        this.empId = empId;
        this.managerId = managerId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
