package com.employeeManagement.employeeService.repository;

import com.employeeManagement.employeeService.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("select i from Employee i where i.email = :email")
    List<Employee> duplicateCheck(@Param(value = "email") String email);

    @Query("select i from Employee i where i.hrId = :hrId")
    List<Employee> employeeListByHrID(@Param(value = "hrId") long hrId);

    @Query("select i from Employee i where i.managerId = :managerId")
    List<Employee> employeeListByManagerId(@Param(value = "managerId") long managerId);
}
