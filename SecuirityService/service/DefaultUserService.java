package com.employeeManagement.securityService.service;

import com.employeeManagement.securityService.dto.UserDTO;
import com.employeeManagement.securityService.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService {
    User save(UserDTO userRegisteredDTO);
}
