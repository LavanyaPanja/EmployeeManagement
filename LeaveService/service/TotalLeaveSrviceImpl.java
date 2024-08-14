package com.employeeManagement.leavesService.service.impl;

import com.employeeManagement.leavesService.dto.TotalLeavesDto;
import com.employeeManagement.leavesService.entity.TotalLeaves;
import com.employeeManagement.leavesService.exception.ResourceNotFoundException;
import com.employeeManagement.leavesService.repository.TotalLeavesRepository;
import com.employeeManagement.leavesService.service.TotalLeavesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TotalLeavesServiceImpl implements TotalLeavesService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TotalLeavesRepository totalLeavesRepository;
    @Override
    public TotalLeavesDto mapToTotalLeavesDto(TotalLeaves leaves) {
        TotalLeavesDto leavesDto = modelMapper.map(leaves,TotalLeavesDto.class);
        return leavesDto;
    }

    @Override
    public TotalLeaves mapToTotalLeaves(TotalLeavesDto leavesDto) {
        TotalLeaves leaves = modelMapper.map(leavesDto,TotalLeaves.class);
        return leaves;
    }

    @Override
    public List<TotalLeavesDto> findAll() {
        return totalLeavesRepository.findAll().stream().map(this::mapToTotalLeavesDto).collect(Collectors.toList());
    }

    @Override
    public TotalLeavesDto findById(long leaveId) {
        Optional<TotalLeaves> result = totalLeavesRepository.findById(leaveId);
        TotalLeaves leaves= null;
        if(result.isPresent()){
            leaves = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToTotalLeavesDto(leaves);
    }

    @Override
    public String deleteById(long leaveId) {
        String message;
        Optional<TotalLeaves> result = totalLeavesRepository.findById(leaveId);
        if(result.isPresent()){
            totalLeavesRepository.deleteById(leaveId);
            message ="Delete Employee Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public TotalLeavesDto save(TotalLeavesDto leavesDto) {
        TotalLeaves leaves = totalLeavesRepository.save(mapToTotalLeaves(leavesDto));
        TotalLeavesDto leavesDto1 = mapToTotalLeavesDto(leaves);
        return leavesDto1;
    }

    @Override
    public TotalLeavesDto update(TotalLeavesDto leavesDto) {
        Optional<TotalLeaves> result = totalLeavesRepository.findById(leavesDto.getId());
        TotalLeaves totalLeaves = null;
        if(result.isPresent()){
            totalLeaves = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        TotalLeaves totalLeaves1 = totalLeavesRepository.save(mapToTotalLeaves(leavesDto));
        TotalLeavesDto totalLeavesDto = mapToTotalLeavesDto(totalLeaves1);
        return totalLeavesDto;
    }

    @Override
    public void updateEarned(long empId, int earned) {
        System.out.println(earned);
        totalLeavesRepository.earned(empId,earned);
    }

    @Override
    public void updateSick(long empId, int sick) {
        System.out.println(sick);
        totalLeavesRepository.updateSick(empId,sick);
    }

    @Override
    public TotalLeavesDto findByEmpId(long empId) {
        TotalLeaves totalLeaves = totalLeavesRepository.findByEmpId(empId);
        return mapToTotalLeavesDto(totalLeaves);
    }
}
