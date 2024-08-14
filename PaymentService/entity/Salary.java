package com.employeeManagement.paymentsService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salary_generator")
    @SequenceGenerator(name = "salary_generator",initialValue = 944501,allocationSize = 1,sequenceName ="salary_seq")
    private long salaryId;
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
    private long monthAmount;
    private long totalAmount;
    @CreationTimestamp
    private LocalDateTime PaymentDate;
    @NotEmpty(message = "Status should be present")
    private String status;

    private long totalExpensesAmount;

    public Salary() {
    }

    public Salary(long empId, String empName, String bankName,long accountNumber,  long monthAmount, LocalDateTime paymentDate, String status,long totalExpensesAmount,long totalAmount) {
        this.empId = empId;
        this.empName = empName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.monthAmount = monthAmount;
        PaymentDate = paymentDate;
        this.status = status;
        this.totalExpensesAmount=totalExpensesAmount;
        this.totalAmount=totalAmount;
    }

    public long getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(long monthAmount) {
        this.monthAmount = monthAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getTotalExpensesAmount() {
        return totalExpensesAmount;
    }

    public void setTotalExpensesAmount(long totalExpensesAmount) {
        this.totalExpensesAmount = totalExpensesAmount;
    }

    public long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(long salaryId) {
        this.salaryId = salaryId;
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


    public LocalDateTime getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
