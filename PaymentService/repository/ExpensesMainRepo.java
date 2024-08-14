package com.employeeManagement.paymentsService.repository;

import com.employeeManagement.paymentsService.entity.ExpensesMain;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ExpensesMainRepository extends JpaRepository<ExpensesMain,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ExpensesMain t set t.amount = :amount, t.status =:status where t.empId = :empId")
    void amountUpdate(@Param(value = "empId") long empId, @Param(value = "amount") long amount,@Param(value = "status")String status);
    @Query("select t from ExpensesMain t where t.empId = :empId")
    ExpensesMain findByEmpId(@Param(value = "empId") long empId);
}
