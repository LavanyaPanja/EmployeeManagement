package com.employeeManagement.leavesService.service.impl;

import com.employeeManagement.leavesService.dto.LeaveNotificationDto;
import com.employeeManagement.leavesService.dto.RequestLeavesDto;
import com.employeeManagement.leavesService.dto.LeavesDto;

import com.employeeManagement.leavesService.entity.Leaves;
import com.employeeManagement.leavesService.entity.TotalLeaves;
import com.employeeManagement.leavesService.exception.NoLeaveFoundException;
import com.employeeManagement.leavesService.exception.ResourceNotFoundException;
import com.employeeManagement.leavesService.openfeign.ApiClientLeaveNotification;
import com.employeeManagement.leavesService.repository.LeaveRepository;
import com.employeeManagement.leavesService.repository.TotalLeavesRepository;
import com.employeeManagement.leavesService.service.LeavesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeavesServiceImpl implements LeavesService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private ApiClientLeaveNotification apiClientLeaveNotification;

    @Autowired
    private TotalLeavesRepository totalLeavesRepository;
    @Override
    public LeavesDto mapToLeavesDto(Leaves leaves) {
        LeavesDto leavesDto = modelMapper.map(leaves,LeavesDto.class);
        return leavesDto;
    }

    @Override
    public Leaves mapToLeaves(LeavesDto leavesDto) {
        Leaves leaves = modelMapper.map(leavesDto,Leaves.class);
        return leaves;
    }

    @Override
    public List<LeavesDto> findAll() {
        return leaveRepository.findAll().stream().map(this::mapToLeavesDto).collect(Collectors.toList());
    }

    @Override
    public LeavesDto findById(long leaveId) {
        Optional<Leaves> result = leaveRepository.findById(leaveId);
        Leaves leaves= null;
        if(result.isPresent()){
            leaves = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return mapToLeavesDto(leaves);
    }

    @Override
    public String deleteById(long leaveId) {
        String message;
        Optional<Leaves> result = leaveRepository.findById(leaveId);
        if(result.isPresent()){
            leaveRepository.deleteById(leaveId);
            message ="Delete Leave Request Successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public LeavesDto save(LeavesDto leavesDto) {
        TotalLeaves totalLeavesDto = totalLeavesRepository.findByEmpId(leavesDto.getEmpId());
        LeavesDto leavesDto1 = new LeavesDto();
        int noDay = leavesDto.getNoDays();
        String cate = leavesDto.getCategory();
        System.out.println(cate.equals("Earned"));
        if(cate.equals("Earned")) {
            int available = totalLeavesDto.getEarned();
            if(noDay>available||available==0){
                throw new NoLeaveFoundException();
            }
            else {
                Leaves leaves = leaveRepository.save(mapToLeaves(leavesDto));
                leavesDto1 = mapToLeavesDto(leaves);
            }
        }
        else if(cate.equals("Sick")){
            int available = totalLeavesDto.getSick();
            if(noDay>available||available==0){
                throw new NoLeaveFoundException();
            }else {
                Leaves leaves = leaveRepository.save(mapToLeaves(leavesDto));
                leavesDto1 = mapToLeavesDto(leaves);
            }

        }
        return leavesDto1;

    }

    @Override
    public LeavesDto update(LeavesDto leavesDto) {
        Optional<Leaves> result = leaveRepository.findById(leavesDto.getLeaveId());
        Leaves leaves = null;
        if(result.isPresent()){
            leaves = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Leaves leaves1 = leaveRepository.save(mapToLeaves(leavesDto));
        LeavesDto leavesDto1 = mapToLeavesDto(leaves1);
        return leavesDto1;
    }

    @Override
    public String approve(RequestLeavesDto requestLeavesDto) {
        String message = "";
        Optional<Leaves> result = leaveRepository.findById(requestLeavesDto.getLeaveId());
        Leaves temp =null;
        if(result.isPresent()){
            String status = "Approved";
            temp=result.get();
            //message ="Approved";
            TotalLeaves test = totalLeavesRepository.findByEmpId(requestLeavesDto.getEmpId());
            int noDay = temp.getNoDays();
            //System.out.println(noDay);
           // System.out.println(temp.getCategory());
            String cate = temp.getCategory();
           // String e = "Earned";
            //System.out.println(cate.equals("Earned"));
           // System.out.println(cate.length());
           // System.out.println(e.length());
            if(cate.equals("Earned")){
                System.out.println(temp.getCategory());
                int temps = test.getEarned();
                if(temps>0&&temps>=noDay) {
                    int earned = temps - noDay;
                    leaveRepository.leaveRequest(requestLeavesDto.getLeaveId(), requestLeavesDto.getEmpId(),requestLeavesDto.getManagerId(),status);
                    totalLeavesRepository.earned(test.getEmpId(), earned);
                    System.out.println(temps - noDay);
                    message = "Approved";
                }
                else {
                    throw new NoLeaveFoundException();

                }
            } else if (cate.equals("Sick")) {
                System.out.println(temp.getCategory());
                int temps = test.getSick();
                if(temps>0&&temps>=noDay){
                    int sick = temps-noDay;
                    leaveRepository.leaveRequest(requestLeavesDto.getLeaveId(), requestLeavesDto.getEmpId(),requestLeavesDto.getManagerId(),status);
                    totalLeavesRepository.updateSick(test.getEmpId(), sick);
                    System.out.println(temps - noDay);
                    message = "Approved";
                }
                else {
                    throw new NoLeaveFoundException();
                }
            }
        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public String reject(RequestLeavesDto requestLeavesDto) {
        String message;
        Optional<Leaves> result = leaveRepository.findById(requestLeavesDto.getLeaveId());
        if(result.isPresent()){
            String status = "Rejected";
            leaveRepository.leaveRequest(requestLeavesDto.getLeaveId(), requestLeavesDto.getEmpId(),requestLeavesDto.getManagerId(),status);
            message ="Rejected";


        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public List<LeavesDto> findByEmpId(long empId) {
        return leaveRepository.findByEmpId(empId).stream().map(this::mapToLeavesDto).collect(Collectors.toList());
    }

    @Override
    public List<LeavesDto> findByEmpIdWithStatus(long employeeId, String status) {
        return leaveRepository.findByEmpIdWithStatus(employeeId,status).stream().map(this::mapToLeavesDto).collect(Collectors.toList());
    }

    @Override
    public List<LeavesDto> findByManagerIdWithStatus(long managerId, String status) {
        return leaveRepository.findByManagerIdWithStatus(managerId,status).stream().map(this::mapToLeavesDto).collect(Collectors.toList());
    }

    @Override
    public List<LeaveNotificationDto> findByIdEmployeeIdOpenFeign(long employeeId) {
        return apiClientLeaveNotification.findByIdEmployeeIdOpenFeign(employeeId);
    }

    @Override
    public String leaveNotificationAdd(LeaveNotificationDto leaveNotificationDto) {
        LeaveNotificationDto leaveNotificationDto1= apiClientLeaveNotification.leaveNotificationAdd(leaveNotificationDto);
        return "Notification Add Successfully";
    }


}
