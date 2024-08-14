package com.employeeManagement.securityService.service;

import com.employeeManagement.securityService.dto.ExpensesDto;
import com.employeeManagement.securityService.dto.RequestDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface HRSecurityService {
    public String expensesApprove(RequestDto requestDto);
    public String expensesReject(RequestDto requestDto);
    public List<ExpensesDto> findExpensesByHrIdOpenFeign( long hrId);
}
