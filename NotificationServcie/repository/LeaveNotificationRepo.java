package com.employeeManagement.notificationService.repository;

import com.employeeManagement.notificationService.entity.LeaveNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveNotificationRepository extends JpaRepository<LeaveNotification,Long> {
    @Query("select t from LeaveNotification t where t.empId = :empId")
    List<LeaveNotification> findByEmpId(@Param(value = "empId") long empId);
}
