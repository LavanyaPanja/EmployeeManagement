package com.employeeManagement.leavesService.service;

import com.employeeManagement.leavesService.dto.LeaveNotificationDto;
import com.employeeManagement.leavesService.dto.RequestLeavesDto;
import com.employeeManagement.leavesService.dto.LeavesDto;
import com.employeeManagement.leavesService.entity.Leaves;

import java.util.List;

public interface LeavesService {
    public LeavesDto mapToLeavesDto(Leaves leaves);
    public Leaves mapToLeaves(LeavesDto leavesDto);

    public List<LeavesDto> findAll();
    public LeavesDto findById(long leaveId);
    public String deleteById(long leaveId);
    public LeavesDto save(LeavesDto leavesDto);
    public LeavesDto update(LeavesDto expensesDto);
    public String approve(RequestLeavesDto requestLeavesDto);
    public String reject(RequestLeavesDto requestLeavesDto);
    public List<LeavesDto> findByEmpId(long empId);
    public List<LeavesDto> findByEmpIdWithStatus(long employeeId,String status);
    public List<LeavesDto> findByManagerIdWithStatus(long managerId,String status);
    public List<LeaveNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId);
    public String leaveNotificationAdd(LeaveNotificationDto leaveNotificationDto);
}
