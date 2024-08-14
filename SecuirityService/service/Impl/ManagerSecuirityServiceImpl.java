package com.employeeManagement.securityService.service.impl;

import com.employeeManagement.securityService.dto.LeavesDto;
import com.employeeManagement.securityService.dto.RequestLeavesDto;
import com.employeeManagement.securityService.openfeign.ApiClientLeaves;
import com.employeeManagement.securityService.service.ManagerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerSecurityServiceImpl implements ManagerSecurityService {

    @Autowired
    private ApiClientLeaves apiClientLeaves;
    @Override
    public String leavesApprove(RequestLeavesDto requestDto) {
        return apiClientLeaves.leavesApprove(requestDto);
    }

    @Override
    public String leavesReject(RequestLeavesDto requestDto) {
        return apiClientLeaves.leavesReject(requestDto);
    }

    @Override
    public List<LeavesDto> findPendingRequestByManager(long managerId) {
        return apiClientLeaves.findPendingRequestByManager(managerId);
    }
}
