package com.employeeManagement.leavesService.repository;

import com.employeeManagement.leavesService.entity.TotalLeaves;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository

public interface TotalLeavesRepository extends JpaRepository<TotalLeaves,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TotalLeaves t set t.earned = :earned where t.empId = :empId")
    void earned(@Param(value = "empId") long empId, @Param(value = "earned") int earned);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TotalLeaves t set t.sick = :sick where t.empId = :empId")
    void updateSick(@Param(value = "empId") long empId, @Param(value = "sick") int sick);

    @Query("select t from TotalLeaves t where t.empId = :empId")
    TotalLeaves findByEmpId(@Param(value = "empId") long empId);
}
