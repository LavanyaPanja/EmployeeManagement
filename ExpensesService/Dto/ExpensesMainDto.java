package com.employeeManagement.expensesService.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ExpensesMainDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "expensesMain_generator")
    @SequenceGenerator(name = "expensesMain_generator",initialValue = 644501,allocationSize = 1,sequenceName ="expenses_main_seq")
    private long Eid;
    @NotNull(message = "Employee Id should be present")
    private long empId;
    @NotEmpty(message = "employee name should be present")
    private String empName;
    @NotEmpty(message = "bank name should be present")
    private String bankName;
    @Column(unique = true)
    @NotNull(message = "Account Number should be present")
    private long accountNumber;
    @NotNull(message = "Amount should be present")
    private long amount;

    //@NotEmpty(message = "Status should be present")
    private String status;

    public ExpensesMainDto() {
    }

    public ExpensesMainDto(long empId, String empName, String bankName, long accountNumber, long amount) {
        this.empId = empId;
        this.empName = empName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public ExpensesMainDto(long Eid, long empId, String empName, String bankName, long accountNumber, long amount) {
        this.Eid = Eid;
        this.empId = empId;
        this.empName = empName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public long getEid() {
        return Eid;
    }

    public void setEid(long Eid) {
        this.Eid = Eid;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
