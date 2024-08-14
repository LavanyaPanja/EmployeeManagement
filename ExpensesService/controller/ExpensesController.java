package com.employeeManagement.expensesService.controller;

import com.employeeManagement.expensesService.dto.ExpensesDto;
import com.employeeManagement.expensesService.dto.ExpensesNotificationDto;
import com.employeeManagement.expensesService.dto.RequestDto;
import com.employeeManagement.expensesService.dto.UpdatePayload;
import com.employeeManagement.expensesService.exception.AlreadyApproveException;
import com.employeeManagement.expensesService.exception.AlreadyRejectedException;
import com.employeeManagement.expensesService.exception.ResourceNotFoundException;
import com.employeeManagement.expensesService.service.impl.ExpensesServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesServiceImpl expensesServiceImpl;
    @GetMapping("/list")
    public ResponseEntity<List<ExpensesDto>> allExpenses(){
        List<ExpensesDto> temp = expensesServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpensesDto> getExpenses(@PathVariable int id){
        ExpensesDto expensesDto = expensesServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenses(@Valid @PathVariable int id){

        ExpensesDto employeeDto = expensesServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        String message = expensesServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/create")
    public ResponseEntity<ExpensesDto> addExpenses(@Valid @RequestBody ExpensesDto expensesDto){
        expensesDto.setStatus("Processing");
        ExpensesDto expensesDto1 =this.expensesServiceImpl.save(expensesDto);
        return new ResponseEntity<>(expensesDto1, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<ExpensesDto> updateExpenses(@Valid @RequestBody ExpensesDto expensesDto) {
        ExpensesDto temp = this.expensesServiceImpl.findById(expensesDto.getExpenseId());
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        temp.setAmount(expensesDto.getAmount());
        temp.setCategory(expensesDto.getCategory());
        temp.setEmployeeId(expensesDto.getEmployeeId());
        temp.setEmployeeName(expensesDto.getEmployeeName());
        temp.setHrId(expensesDto.getHrId());
        temp.setStatus(expensesDto.getStatus());
        expensesServiceImpl.update(temp);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @PutMapping("/approve")
    public ResponseEntity<String> approve(@Valid @RequestBody RequestDto requestDto) {
        ExpensesDto temp = this.expensesServiceImpl.findById(requestDto.getExpenseId());
        String message = "";
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        if(!temp.getStatus().equals("Approved")&&!temp.getStatus().equals("Rejected")){
            message = expensesServiceImpl.approve(requestDto);
            UpdatePayload updatePayload = new UpdatePayload();
            updatePayload.setAmount(temp.getAmount());
            updatePayload.setEmpId(requestDto.getEmployeeId());
            System.out.println(temp.getAmount());
            System.out.println(requestDto.getEmployeeId());
            String m = expensesServiceImpl.updateAmount(updatePayload);
            ExpensesNotificationDto expensesNotificationDto = new ExpensesNotificationDto();
            ExpensesDto e = expensesServiceImpl.findById(requestDto.getExpenseId());
            String description =" "+temp.getEmployeeName()+" Your Expenses is Approve By "+temp.getHrId()+"  Amount "+temp.getAmount()+" With Status "+e.getStatus();
            expensesNotificationDto.setEmpId(requestDto.getEmployeeId());
            expensesNotificationDto.setDescription(description);
            String m1 = expensesServiceImpl.expensesNotificationAdd(expensesNotificationDto);
        }else {
                throw new AlreadyApproveException();
        }

        return ResponseEntity.ok(message);
    }
    @PutMapping("/reject")
    public ResponseEntity<String> reject(@Valid @RequestBody RequestDto requestDto) {
        ExpensesDto temp = this.expensesServiceImpl.findById(requestDto.getExpenseId());
        String message = "";
        if (temp == null) {
            throw new ResourceNotFoundException();
        }
        if(!temp.getStatus().equals("Rejected")&&!temp.getStatus().equals("Approved")) {
            message = expensesServiceImpl.reject(requestDto);
            ExpensesNotificationDto expensesNotificationDto = new ExpensesNotificationDto();
            ExpensesDto e = expensesServiceImpl.findById(requestDto.getExpenseId());
            String description = " " + temp.getEmployeeName() + " Your Expenses is Rejected By " + temp.getHrId() + "  Amount " + temp.getAmount() + " With Status " + e.getStatus();
            expensesNotificationDto.setEmpId(requestDto.getEmployeeId());
            expensesNotificationDto.setDescription(description);
            String m1 = expensesServiceImpl.expensesNotificationAdd(expensesNotificationDto);
        }else {
            throw new AlreadyRejectedException();
        }
        return ResponseEntity.ok(message);
    }


    @GetMapping("/list/{employeeId}")
    public ResponseEntity<List<ExpensesDto>> allExpenseByEmpID(@PathVariable long employeeId){
        List<ExpensesDto> temp = expensesServiceImpl.findByEmpId(employeeId);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/pending/list/{employeeId}")
    public ResponseEntity<List<ExpensesDto>> allExpenseByEmpIDWithStatus(@PathVariable long employeeId){
        String s = "Processing";
        List<ExpensesDto> temp = expensesServiceImpl.findByEmpIdWithStatus(employeeId,s);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/hr/pending/list/{hrId}")
    public ResponseEntity<List<ExpensesDto>> allExpenseByHrIdWithStatus(@PathVariable long hrId){
        String s = "Processing";
        List<ExpensesDto> temp = expensesServiceImpl.findByManagerIdWithStatus(hrId,s);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/notification/{empId}")
    public ResponseEntity<List<ExpensesNotificationDto>> allExpensesNotificationByEmpId(@PathVariable long empId){
        List<ExpensesNotificationDto> temp = expensesServiceImpl.findByIdEmployeeIdOpenFeign(empId);
        return ResponseEntity.ok(temp);
    }

}
