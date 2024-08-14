package com.employeeManagement.leavesService.service;



import com.employeeManagement.leavesService.dto.TotalLeavesDto;
import com.employeeManagement.leavesService.entity.TotalLeaves;

import java.util.List;

public interface TotalLeavesService {
    public TotalLeavesDto mapToTotalLeavesDto(TotalLeaves leaves);
    public TotalLeaves mapToTotalLeaves(TotalLeavesDto leavesDto);

    public List<TotalLeavesDto> findAll();
    public TotalLeavesDto findById(long leaveId);
    public String deleteById(long leaveId);
    public TotalLeavesDto save(TotalLeavesDto leavesDto);
    public TotalLeavesDto update(TotalLeavesDto leavesDto);
    public void updateEarned(long empId, int earned);
    public void updateSick(long empId, int sick);
    public TotalLeavesDto findByEmpId(long empId);
}
