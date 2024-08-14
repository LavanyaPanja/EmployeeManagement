package com.employeeManagement.paymentsService.repository;

import com.employeeManagement.paymentsService.entity.Salary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Long> {
    @Query("select i from Salary i where i.accountNumber = :accountNumber")
    List<Salary> duplicateCheck(@Param(value = "accountNumber") long accountNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Salary t set t.totalExpensesAmount = :totalExpensesAmount where t.empId = :empId")
    void totalAmountUpdate(@Param(value = "empId") long empId, @Param(value = "totalExpensesAmount") long totalExpensesAmount);

    @Query("select i from Salary i where i.empId = :empId")
    Salary findByEmpId(@Param(value = "empId") long empId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Salary t set t.PaymentDate = :PaymentDate,t.totalAmount=:totalAmount where t.empId = :empId")
    void totalPaymentDate(@Param(value = "empId") long empId, @Param(value = "PaymentDate") LocalDateTime PaymentDate,@Param(value = "totalAmount") long totalAmount);

}
