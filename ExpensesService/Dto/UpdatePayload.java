package com.employeeManagement.expensesService.dto;

public class UpdatePayload {
    private long amount;
    private long empId;

    public UpdatePayload() {
    }

    public UpdatePayload(long amount, long empId) {
        this.amount = amount;
        this.empId = empId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }
}
