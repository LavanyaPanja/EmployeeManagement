package com.employeeManagement.securityService.service.impl;

import com.employeeManagement.securityService.dto.ExpensesDto;
import com.employeeManagement.securityService.dto.RequestDto;
import com.employeeManagement.securityService.openfeign.ApiClientExpenses;
import com.employeeManagement.securityService.service.HRSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HRSecurityServiceImpl implements HRSecurityService {
    @Autowired
    private ApiClientExpenses apiClientExpenses;
    @Override
    public String expensesApprove(RequestDto requestDto) {
        return apiClientExpenses.expensesApprove(requestDto);
    }

    @Override
    public String expensesReject(RequestDto requestDto) {
        return apiClientExpenses.expensesReject(requestDto);
    }

    @Override
    public List<ExpensesDto> findExpensesByHrIdOpenFeign(long hrId) {
        return apiClientExpenses.findExpensesByHrIdOpenFeign(hrId);
    }
}
