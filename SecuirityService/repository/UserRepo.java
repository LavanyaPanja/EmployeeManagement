package com.employeeManagement.securityService.repository;

import com.employeeManagement.securityService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);
}
