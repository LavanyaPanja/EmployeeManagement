package com.employeeManagement.employeeService.controller;

import com.employeeManagement.employeeService.dto.*;
import com.employeeManagement.employeeService.exception.DuplicateUsernameException;
import com.employeeManagement.employeeService.exception.ExpensesNotFoundException;
import com.employeeManagement.employeeService.exception.ResourceNotFoundException;
import com.employeeManagement.employeeService.exception.SalaryNotFoundException;
import com.employeeManagement.employeeService.service.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    @GetMapping("/list/{hrId}")
    public ResponseEntity<List<EmployeeDto>> allEmployeeByHr(@PathVariable long hrId){
        List<EmployeeDto> temp = employeeServiceImpl.employeeListByHr(hrId);
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/manager/list/{managerId}")
    public ResponseEntity<List<EmployeeDto>> allEmployeeByManager(@PathVariable long managerId){
        List<EmployeeDto> temp = employeeServiceImpl.employeeListByManagerId(managerId);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id){
        try {
            EmployeeDto employeeDto = employeeServiceImpl.findById(id);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Employee get by employee id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(employeeDto);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@Valid @PathVariable long id){

        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        String message = employeeServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> addRegistration(@Valid @RequestBody EmployeeDto employeeDto){
        List<EmployeeDto> employeeDtoS = employeeServiceImpl.duplicateCheckService(employeeDto.getEmail());
        System.out.println(employeeDtoS.size());
        if(employeeDtoS.size()>0){
            throw new DuplicateUsernameException();
        }
        EmployeeDto employeeDto1 =this.employeeServiceImpl.save(employeeDto);
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateRegistration(@Valid @RequestBody EmployeeDto employeeDto){
        EmployeeDto temp = this.employeeServiceImpl.findById(employeeDto.getEmpId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        //temp.setEmpId(employeeDto.getEmpId());
        temp.setEmpName(employeeDto.getEmpName());
        temp.setEmail(employeeDto.getEmail());
        temp.setWorkLocation(employeeDto.getWorkLocation());
        temp.setManagerId(employeeDto.getManagerId());
        temp.setReportingManager(employeeDto.getReportingManager());
        temp.setRole(employeeDto.getRole());
        temp.setStatus(employeeDto.getStatus());
        temp.setHrName(employeeDto.getHrName());
        temp.setHrId(employeeDto.getHrId());
        employeeServiceImpl.update(temp);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }
    //expenses-service

    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpensesDto> getExpenses(@PathVariable long id){
        ExpensesDto expensesDto = employeeServiceImpl.findByIdForExpensesOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by expenses id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
    }
    @GetMapping("/expenses/list/{id}")
    public ResponseEntity<List<ExpensesDto>> getListOfExpenses(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<ExpensesDto> expensesDto = employeeServiceImpl.findByEmpIdForExpensesOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
    }
    @GetMapping("/expenses/pending/list/{id}")
    public ResponseEntity<List<ExpensesDto>> getListOfExpensesWithStatus(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<ExpensesDto> expensesDto = employeeServiceImpl.findByIdEmployeeIdWithStatusOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by employee id with status");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
    }
    @PostMapping("/expenses/create")
    public ResponseEntity<String> createExpenses(@Valid @RequestBody ExpensesDto expensesDto){
        EmployeeDto employeeDtoS = employeeServiceImpl.findById(expensesDto.getEmployeeId());
        //System.out.println(employeeDtoS.size());
        if(employeeDtoS==null){
            throw new ResourceNotFoundException();
        }
        expensesDto.setStatus("Processing");
        ExpensesDto expensesDto1 =this.employeeServiceImpl.createExpensesByEmployee(expensesDto);
       // return new ResponseEntity<>(expensesDto1, HttpStatus.CREATED);
        String m = "Create Expenses Item With Following Category "+expensesDto1.getCategory()+", Amount: "+expensesDto1.getAmount()+" With "+expensesDto1.getStatus()+" State.";
        return ResponseEntity.ok(m);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> deleteExpenses(@Valid @PathVariable long id){

        ExpensesDto expensesDto = employeeServiceImpl.findByIdForExpensesOpenFeign(id);
        if(expensesDto==null){
            throw new ExpensesNotFoundException();
        }
        String message = employeeServiceImpl.deleteExpensesById(id);
        return ResponseEntity.ok(message);
    }
    //leave-service
    @GetMapping("/leaves/{id}")
    public ResponseEntity<LeavesDto> getLeave(@PathVariable long id){
        LeavesDto leavesDto = employeeServiceImpl.findByIdForLeavesOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves get by Leaves id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }

    @GetMapping("/leaves/list/{id}")
    public ResponseEntity<List<LeavesDto>> getListOfLeaves(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<LeavesDto> leavesDto = employeeServiceImpl.findByEmpIdForLeavesOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }
    @GetMapping("/leaves/pending/list/{id}")
    public ResponseEntity<List<LeavesDto>> getListOfLeavesWithStatus(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<LeavesDto> leavesDto = employeeServiceImpl.findByIdLeaveEmployeeIdWithStatusOpenFeign(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by employee id with status");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
    }

    @PostMapping("/leaves/request")
    public ResponseEntity<String> createNewRequestForLeaves(@Valid @RequestBody LeavesDto leavesDto){
        EmployeeDto employeeDtoS = employeeServiceImpl.findById(leavesDto.getEmpId());
        //System.out.println(employeeDtoS.size());
        if(employeeDtoS==null){
            throw new ResourceNotFoundException();
        }
        leavesDto.setStatus("Processing");
        LeavesDto leavesDto1 =this.employeeServiceImpl.createLeavesByEmployee(leavesDto);
        // return new ResponseEntity<>(expensesDto1, HttpStatus.CREATED);
        String m = "Leave Request Raised With Following Category "+leavesDto1.getCategory()+", No Days: "+leavesDto1.getNoDays()+" With "+leavesDto1.getStatus()+" State.";
        return ResponseEntity.ok(m);
    }
    @PutMapping("/leaves/edit")
    public ResponseEntity<String> editLeaves(@Valid @RequestBody LeavesDto leavesDto){
        LeavesDto leavesDto1 = employeeServiceImpl.editLeavesOpenFeign(leavesDto);
        String m = "Leave Successfully edited";
        return ResponseEntity.ok(m);
    }
    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<String> deleteLeaves(@Valid @PathVariable long id){

        LeavesDto leavesDto = employeeServiceImpl.findByIdForLeavesOpenFeign(id);
        if(leavesDto==null){
            throw new ExpensesNotFoundException();
        }
        String message = employeeServiceImpl.deleteLeavesById(id);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/total/leaves/{id}")
    public ResponseEntity<TotalLeavesDto> totalLeaves(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        TotalLeavesDto totalLeavesDto = employeeServiceImpl.totalLeavesId(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(totalLeavesDto);
    }

    @GetMapping("/salary/{id}")
    public ResponseEntity<SalaryDto> getSalary(@PathVariable long id){
        try {
            SalaryDto salaryDto = employeeServiceImpl.findByEmpIdForSalaryOpenFeign(id);
            if (salaryDto == null) {
                throw new SalaryNotFoundException();
            }
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Salary get by employee id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto);
        }catch (Exception ex){
            throw new SalaryNotFoundException();
        }
    }

    @GetMapping("/leave/notification/{id}")
    public ResponseEntity<List<LeaveNotificationDto>> leavesNotification(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<LeaveNotificationDto> leaveNotificationDto = employeeServiceImpl.leavesNotification(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leave Notification get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leaveNotificationDto);
    }
    @GetMapping("/expenses/notification/{id}")
    public ResponseEntity<List<ExpensesNotificationDto>> expensesNotification(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<ExpensesNotificationDto> expensesNotification = employeeServiceImpl.expensesNotification(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses Notification get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesNotification);
    }
    @GetMapping("/salary/notification/{id}")
    public ResponseEntity<List<SalaryNotificationDto>> salaryNotification(@PathVariable long id){
        EmployeeDto employeeDto = employeeServiceImpl.findById(id);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<SalaryNotificationDto> salaryNotification = employeeServiceImpl.salaryNotification(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leave Notification get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryNotification);
    }

}
