package com.employeeManagement.expensesService.repository;

import com.employeeManagement.expensesService.entity.Expenses;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Expenses i set i.status = :status where i.expenseId = :expenseId AND i.employeeId=:employeeId AND i.hrId =:hrId")
    void updateStatus(@Param(value = "expenseId") long expenseId, @Param(value = "employeeId") long employeeId ,@Param(value = "hrId") long hrId, @Param(value = "status") String status);

    @Query("select t from Expenses t where t.employeeId = :employeeId")
    List<Expenses> findByEmpId(@Param(value = "employeeId") long employeeId);
    @Query("select t from Expenses t where t.employeeId = :employeeId AND t.status =:status")
    List<Expenses> findByEmpIdWithStatus(@Param(value = "employeeId") long employeeId,@Param(value = "status") String status);

    @Query("select t from Expenses t where t.hrId = :hrId AND t.status =:status")
    List<Expenses> findByManagerIdWithStatus(@Param(value = "hrId") long hrId,@Param(value = "status") String status);

}
