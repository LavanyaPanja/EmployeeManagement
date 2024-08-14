package com.employeeManagement.notificationService.controller;

import com.employeeManagement.notificationService.dto.ExpensesNotificationDto;
import com.employeeManagement.notificationService.dto.LeaveNotificationDto;
import com.employeeManagement.notificationService.dto.SalaryNotificationDto;
import com.employeeManagement.notificationService.exception.ResourceNotFoundException;
import com.employeeManagement.notificationService.service.impl.ExpensesNotificationServiceImpl;
import com.employeeManagement.notificationService.service.impl.LeaveNotificationServiceImpl;
import com.employeeManagement.notificationService.service.impl.SalaryNotificationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private LeaveNotificationServiceImpl leaveNotificationServiceImpl;

    @Autowired
    private ExpensesNotificationServiceImpl expensesNotificationServiceImpl;
    @Autowired
    private SalaryNotificationServiceImpl salaryNotificationServiceImpl;
    @GetMapping("/list")
    public ResponseEntity<List<LeaveNotificationDto>> allLeaveNotification(){
        List<LeaveNotificationDto> temp = leaveNotificationServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveNotificationDto> getLeaveNotificationId(@PathVariable long id){
        LeaveNotificationDto leaveNotificationDto = leaveNotificationServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves Notification get by leaves id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leaveNotificationDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveNotification(@Valid @PathVariable long id){

        LeaveNotificationDto leaveNotificationDto = leaveNotificationServiceImpl.findById(id);
        if(leaveNotificationDto==null){
            throw new ResourceNotFoundException();
        }
        String message = leaveNotificationServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/create")
    public ResponseEntity<LeaveNotificationDto> leaveNotificationRequestByUser(@Valid @RequestBody LeaveNotificationDto leavesDto){
        LeaveNotificationDto leaveNotificationDto =this.leaveNotificationServiceImpl.save(leavesDto);
        return new ResponseEntity<>(leaveNotificationDto, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<LeaveNotificationDto>> leaveNotificationByEmpId(@PathVariable long empId){
        List<LeaveNotificationDto> temp = leaveNotificationServiceImpl.findByEmpId(empId);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/expenses/list")
    public ResponseEntity<List<ExpensesNotificationDto>> allExpensesNotification(){
        List<ExpensesNotificationDto> temp = expensesNotificationServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpensesNotificationDto> getExpensesNotificationId(@PathVariable long id){
        ExpensesNotificationDto expensesNotificationDto = expensesNotificationServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves Notification get by leaves id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesNotificationDto);
    }
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> deleteExpensesNotification(@Valid @PathVariable long id){

        ExpensesNotificationDto expensesNotificationDto = expensesNotificationServiceImpl.findById(id);
        if(expensesNotificationDto==null){
            throw new ResourceNotFoundException();
        }
        String message = expensesNotificationServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/expenses/create")
    public ResponseEntity<ExpensesNotificationDto> expensesNotificationRequestByUser(@Valid @RequestBody ExpensesNotificationDto expensesNotificationDto){
        ExpensesNotificationDto expensesNotificationDto1 =this.expensesNotificationServiceImpl.save(expensesNotificationDto);
        return new ResponseEntity<>(expensesNotificationDto1, HttpStatus.CREATED);
    }

    @GetMapping("/expenses/employee/{empId}")
    public ResponseEntity<List<ExpensesNotificationDto>> expensesNotificationByEmpId(@PathVariable long empId){
        List<ExpensesNotificationDto> temp = expensesNotificationServiceImpl.findByEmpId(empId);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/salary/expenses/list")
    public ResponseEntity<List<SalaryNotificationDto>> allSalaryNotification(){
        List<SalaryNotificationDto> temp = salaryNotificationServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/salary/expenses/{id}")
    public ResponseEntity<SalaryNotificationDto> getSalaryNotificationId(@PathVariable long id){
        SalaryNotificationDto salaryNotificationDto = salaryNotificationServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses Payment Notification get by leaves id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryNotificationDto);
    }
    @DeleteMapping("/salary/expenses/{id}")
    public ResponseEntity<String> deleteSalaryNotification(@Valid @PathVariable long id){

        SalaryNotificationDto salaryNotificationDto = salaryNotificationServiceImpl.findById(id);
        if(salaryNotificationDto==null){
            throw new ResourceNotFoundException();
        }
        String message = salaryNotificationServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/salary/expenses/create")
    public ResponseEntity<SalaryNotificationDto> salaryNotificationRequestByUser(@Valid @RequestBody SalaryNotificationDto salaryNotificationDto){
        SalaryNotificationDto salaryNotificationDto1 =this.salaryNotificationServiceImpl.save(salaryNotificationDto);
        return new ResponseEntity<>(salaryNotificationDto1, HttpStatus.CREATED);
    }

    @GetMapping("/salary/expenses/employee/{empId}")
    public ResponseEntity<List<SalaryNotificationDto>> salaryNotificationByEmpId(@PathVariable long empId){
        List<SalaryNotificationDto> temp = salaryNotificationServiceImpl.findByEmpId(empId);
        return ResponseEntity.ok(temp);
    }
}
