package com.employeeManagement.securityService.service;

import com.employeeManagement.securityService.dto.LeavesDto;
import com.employeeManagement.securityService.dto.RequestLeavesDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ManagerSecurityService {
    public String leavesApprove(RequestLeavesDto requestDto);
    public String leavesReject(RequestLeavesDto requestDto);

    public List<LeavesDto> findPendingRequestByManager(long managerId);
}
