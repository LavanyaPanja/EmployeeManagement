package com.employeeManagement.expensesService.dto;

import jakarta.validation.constraints.NotNull;

public class RequestDto {
    @NotNull(message = "Should be present value for expense Id filed")
    private long expenseId;
    @NotNull(message = "Should be present value for employee Id filed")
    private long employeeId;
    @NotNull(message = "Should be present value for hr Id filed")
    private long hrId;

    private String status;
    private String category;
    public RequestDto() {
    }

    public RequestDto(long expenseId,long employeeId,long hrId) {
        this.expenseId = expenseId;
        this.employeeId = employeeId;
        this.hrId = hrId;
    }

    public long getHrId() {
        return hrId;
    }

    public void setHrId(long hrId) {
        this.hrId = hrId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
