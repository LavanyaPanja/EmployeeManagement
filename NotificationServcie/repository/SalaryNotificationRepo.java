package com.employeeManagement.notificationService.repository;


import com.employeeManagement.notificationService.entity.SalaryNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryNotificationRepository extends JpaRepository<SalaryNotification,Long> {
    @Query("select t from SalaryNotification t where t.empId = :empId")
    List<SalaryNotification> findByEmpId(@Param(value = "empId") long empId);
}
