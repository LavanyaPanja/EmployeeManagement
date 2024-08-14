package com.employeeManagement.leavesService.repository;

import com.employeeManagement.leavesService.entity.Leaves;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Leaves i set i.status = :status where i.leaveId = :leaveId AND i.empId=:empId AND i.managerId=:managerId")
    void leaveRequest(@Param(value = "leaveId") long leaveId,@Param(value = "empId") long empId ,@Param(value = "managerId") long managerId,@Param(value = "status") String status);

    @Query("select t from Leaves t where t.empId = :empId")
    List<Leaves> findByEmpId(@Param(value = "empId") long empId);
    @Query("select t from Leaves t where t.empId = :empId AND t.status =:status")
    List<Leaves> findByEmpIdWithStatus(@Param(value = "empId") long empId,@Param(value = "status") String status);

    @Query("select t from Leaves t where t.managerId = :managerId AND t.status =:status")
    List<Leaves> findByManagerIdWithStatus(@Param(value = "managerId") long empId,@Param(value = "status") String status);

}
