package com.employeeManagement.leavesService.controller;

import com.employeeManagement.leavesService.dto.LeaveNotificationDto;
import com.employeeManagement.leavesService.dto.RequestLeavesDto;
import com.employeeManagement.leavesService.dto.LeavesDto;
import com.employeeManagement.leavesService.dto.TotalLeavesDto;
import com.employeeManagement.leavesService.exception.AlreadyApproveException;
import com.employeeManagement.leavesService.exception.ResourceNotFoundException;
import com.employeeManagement.leavesService.service.impl.LeavesServiceImpl;
import com.employeeManagement.leavesService.service.impl.TotalLeavesServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/leaves")
public class LeavesController {
    @Autowired
    private LeavesServiceImpl leavesServiceImpl;

    @Autowired
    private TotalLeavesServiceImpl totalLeavesServiceImpl;

    @GetMapping("/list")
    public ResponseEntity<List<LeavesDto>> allLeaves(){
        List<LeavesDto> temp = leavesServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeavesDto> getLeaveId(@PathVariable long id){
        LeavesDto leavesDto = leavesServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves get by leaves id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeave(@Valid @PathVariable long id){

        LeavesDto leavesDto = leavesServiceImpl.findById(id);
        if(leavesDto==null){
            throw new ResourceNotFoundException();
        }
        String message = leavesServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/request")
    public ResponseEntity<LeavesDto> leaveRequestByUser(@Valid @RequestBody LeavesDto leavesDto){
        leavesDto.setStatus("Processing");
        LeavesDto leavesDto1 =this.leavesServiceImpl.save(leavesDto);
        return new ResponseEntity<>(leavesDto1, HttpStatus.CREATED);
    }

    @PutMapping("/approve")
    public ResponseEntity<String> approve(@Valid @RequestBody RequestLeavesDto approveDto) {
        LeavesDto temp = this.leavesServiceImpl.findById(approveDto.getLeaveId());
        String message ="";
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        if(!temp.getStatus().equals("Approved")&&!temp.getStatus().equals("Rejected")){

            message = leavesServiceImpl.approve(approveDto);
            LeaveNotificationDto leaveNotificationDto = new LeaveNotificationDto();
            String description = " " + temp.getEmpName() + " Your Leaves is Approve By " + temp.getReportingManager() + "  To date: " + temp.getToDate() + " And " + temp.getEndDate();
            leaveNotificationDto.setEmpId(approveDto.getEmpId());
            leaveNotificationDto.setDescription(description);
            String m = leavesServiceImpl.leaveNotificationAdd(leaveNotificationDto);
        }
        else {
            throw new AlreadyApproveException();
        }
        return ResponseEntity.ok(message);
    }
    @PutMapping("/reject")
    public ResponseEntity<String> reject(@Valid @RequestBody RequestLeavesDto requestLeavesDto) {
        LeavesDto temp = this.leavesServiceImpl.findById(requestLeavesDto.getLeaveId());
        String message="";
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        if(!temp.getStatus().equals("Rejected")&&!temp.getStatus().equals("Approved")){
            message = leavesServiceImpl.reject(requestLeavesDto);
            LeaveNotificationDto leaveNotificationDto = new LeaveNotificationDto();
            String description = " " + temp.getEmpName() + " Your Leaves is Rejected By " + temp.getReportingManager() + "  To date: " + temp.getToDate() + " And " + temp.getEndDate();
            leaveNotificationDto.setEmpId(requestLeavesDto.getEmpId());
            leaveNotificationDto.setDescription(description);
            String m = leavesServiceImpl.leaveNotificationAdd(leaveNotificationDto);
        }
        else {
            throw new AlreadyApproveException();
        }
        return ResponseEntity.ok(message);
    }

    //total leave apis
    @GetMapping("/total/list")
    public ResponseEntity<List<TotalLeavesDto>> allTotalLeaves(){
        List<TotalLeavesDto> temp = totalLeavesServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/total/{id}")
    public ResponseEntity<TotalLeavesDto> getTotalLeaveId(@PathVariable int id){
        TotalLeavesDto leavesDto = totalLeavesServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }

    @GetMapping("/total/employee/{id}")
    public ResponseEntity<TotalLeavesDto> getLeaveByEmpId(@PathVariable int id){
        TotalLeavesDto leavesDto = totalLeavesServiceImpl.findByEmpId(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }
    @DeleteMapping("/total/{id}")
    public ResponseEntity<String> deleteTotalLeave(@Valid @PathVariable int id){

        TotalLeavesDto leavesDto = totalLeavesServiceImpl.findById(id);
        if(leavesDto==null){
            throw new ResourceNotFoundException();
        }
        String message = leavesServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/total/request")
    public ResponseEntity<TotalLeavesDto> leaveRequestByUser(@Valid @RequestBody TotalLeavesDto leavesDto){

        TotalLeavesDto leavesDto1 =this.totalLeavesServiceImpl.save(leavesDto);
        return new ResponseEntity<>(leavesDto1, HttpStatus.CREATED);
    }
    @PutMapping("/test/{id}")
    public ResponseEntity<String> testUpdate(@PathVariable long id){
        totalLeavesServiceImpl.updateEarned(id,25);
        return ResponseEntity.ok("update");
    }

    @PutMapping("/sick/{id}")
    public ResponseEntity<String> testSick(@PathVariable long id){
        totalLeavesServiceImpl.updateSick(id,6);
        return ResponseEntity.ok("update");
    }
    @GetMapping("/list/{employeeId}")
    public ResponseEntity<List<LeavesDto>> allExpenseByEmpID(@PathVariable long employeeId){
        List<LeavesDto> temp = leavesServiceImpl.findByEmpId(employeeId);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/pending/list/{employeeId}")
    public ResponseEntity<List<LeavesDto>> allExpenseByEmpIDWithStatus(@PathVariable long employeeId){
        String s = "Processing";
        List<LeavesDto> temp = leavesServiceImpl.findByEmpIdWithStatus(employeeId,s);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/manager/pending/list/{managerId}")
    public ResponseEntity<List<LeavesDto>> allManagerByEmpID(@PathVariable long managerId){
        String s = "Processing";
        List<LeavesDto> temp = leavesServiceImpl.findByManagerIdWithStatus(managerId,s);
        return ResponseEntity.ok(temp);
    }

    @PutMapping("/edit")
    public ResponseEntity<LeavesDto> updateExpenses(@Valid @RequestBody LeavesDto leavesDto) {
        LeavesDto temp = this.leavesServiceImpl.findById(leavesDto.getLeaveId());
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        temp.setCategory(leavesDto.getCategory());
        temp.setEmpId(leavesDto.getEmpId());
        temp.setEmpName(leavesDto.getEmpName());
        temp.setEndDate(leavesDto.getEndDate());
        temp.setToDate(leavesDto.getToDate());
        temp.setManagerId(leavesDto.getManagerId());
        temp.setNoDays(leavesDto.getNoDays());
        temp.setReportingManager(leavesDto.getReportingManager());
        //temp.setStatus(leavesDto.getStatus());
        leavesServiceImpl.update(temp);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }
    @GetMapping("/notification/{empId}")
    public ResponseEntity<List<LeaveNotificationDto>> allLeaveNotificationByEmpId(@PathVariable long empId){
        List<LeaveNotificationDto> temp = leavesServiceImpl.findByIdEmployeeIdOpenFeign(empId);
        return ResponseEntity.ok(temp);
    }

}
