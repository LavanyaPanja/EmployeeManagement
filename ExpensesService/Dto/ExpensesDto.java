package com.employeeManagement.expensesService.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class ExpensesDto {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "expenses_generator")
    @SequenceGenerator(name = "expenses_generator",initialValue = 55001,allocationSize = 1,sequenceName ="expenses_seq")
    private long expenseId;
    @NotEmpty(message = "category should be present")
    @Size(min = 3,message = "category should be more than three letters")
    private String category;
    @NotNull(message = "Amount should be present")
    private long amount;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @NotNull(message = "employee Id should be present")
    private long employeeId;
    @NotEmpty(message = "employee name should be present")
    private String employeeName;
    @NotNull(message = "HR Id should be present")
    private long hrId;
    //@NotEmpty(message = "status should be present")
    private String status;

    public ExpensesDto() {
    }

    public ExpensesDto(String category, long amount, LocalDateTime createdDate, long employeeId, String employeeName, long hrId) {
        this.category = category;
        this.amount = amount;
        this.createdDate = createdDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.hrId = hrId;
        //this.status = status;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
