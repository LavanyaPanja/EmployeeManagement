package com.employeeManagement.notificationService.repository;

import com.employeeManagement.notificationService.entity.ExpensesNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesNotificationRepository extends JpaRepository<ExpensesNotification,Long> {
    @Query("select t from ExpensesNotification t where t.empId = :empId")
    List<ExpensesNotification> findByEmpId(@Param(value = "empId") long empId);
}
