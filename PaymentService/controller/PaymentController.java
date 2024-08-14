package com.employeeManagement.paymentsService.controller;

import com.employeeManagement.paymentsService.dto.ExpensesMainDto;
import com.employeeManagement.paymentsService.dto.SalaryDto;
import com.employeeManagement.paymentsService.dto.SalaryNotificationDto;
import com.employeeManagement.paymentsService.dto.UpdatePayload;
import com.employeeManagement.paymentsService.exception.AlreadyApproveException;
import com.employeeManagement.paymentsService.exception.DuplicateUsernameException;
import com.employeeManagement.paymentsService.exception.ResourceNotFoundException;
import com.employeeManagement.paymentsService.service.impl.ExpensesMainServiceImpl;
import com.employeeManagement.paymentsService.service.impl.SalaryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private SalaryServiceImpl salaryServiceImpl;
    @Autowired
    private ExpensesMainServiceImpl expensesMainServiceImpl;
    @GetMapping("/list")
    public ResponseEntity<List<SalaryDto>> salaryForAllEmployee(){
        List<SalaryDto> temp = salaryServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryDto> salaryForGetEmployee(@PathVariable long id){
        SalaryDto salaryDto = salaryServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> salaryForDeleteEmployee(@Valid @PathVariable long id){

        SalaryDto salaryDto = salaryServiceImpl.findById(id);
        if(salaryDto==null){
            throw new ResourceNotFoundException();
        }
        String message = salaryServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/create")
    public ResponseEntity<SalaryDto> addSalaryDetail(@Valid @RequestBody SalaryDto salaryDto){
        System.out.println(salaryDto.getAccountNumber());
        List<SalaryDto> s = salaryServiceImpl.duplicateCheckService(salaryDto.getAccountNumber());
        System.out.println(s.size());
        System.out.println();
        if(s.size()>0){
            throw new DuplicateUsernameException();
        }
        salaryDto.setStatus("Active");
        SalaryDto salaryDto1 =this.salaryServiceImpl.save(salaryDto);
        return new ResponseEntity<>(salaryDto1, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<SalaryDto> updateSalaryDetails(@Valid @RequestBody SalaryDto salaryDto){
        System.out.println(salaryDto.getAccountNumber());
        SalaryDto temp = this.salaryServiceImpl.findById(salaryDto.getSalaryId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        temp.setEmpId(salaryDto.getEmpId());
        temp.setEmpName(salaryDto.getEmpName());
        temp.setBankName(salaryDto.getBankName());
        temp.setAccountNumber(salaryDto.getAccountNumber());
        temp.setMonthAmount(salaryDto.getMonthAmount());

        //temp.setPaymentDate(salaryDto.getPaymentDate());

        salaryServiceImpl.update(temp);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping("/expenses/list")
    public ResponseEntity<List<ExpensesMainDto>> expensesForAllEmployee(){
        List<ExpensesMainDto> temp = expensesMainServiceImpl.findAll();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpensesMainDto> expensesForGetEmployee(@PathVariable long id){
        ExpensesMainDto expensesMainDto = expensesMainServiceImpl.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesMainDto);
    }
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> expensesForDeleteEmployee(@Valid @PathVariable long id){

        ExpensesMainDto expensesMainDto = expensesMainServiceImpl.findById(id);
        if(expensesMainDto==null){
            throw new ResourceNotFoundException();
        }
        String message = expensesMainServiceImpl.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/expenses/create")
    public ResponseEntity<ExpensesMainDto> addExpensesDetail(@Valid @RequestBody ExpensesMainDto expensesMainDto){
        expensesMainDto.setStatus("Processing");
        ExpensesMainDto expensesMainDto1 =this.expensesMainServiceImpl.save(expensesMainDto);
        return new ResponseEntity<>(expensesMainDto1, HttpStatus.CREATED);
    }
    @PutMapping("/expenses/update")
    public ResponseEntity<ExpensesMainDto> updateExpensesDetails(@Valid @RequestBody ExpensesMainDto expensesMainDto){
        System.out.println(expensesMainDto.getEid());
        ExpensesMainDto temp = this.expensesMainServiceImpl.findById(expensesMainDto.getEid());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        temp.setEmpId(expensesMainDto.getEmpId());
        temp.setEmpName(expensesMainDto.getEmpName());
        temp.setBankName(expensesMainDto.getBankName());
        temp.setAccountNumber(expensesMainDto.getAccountNumber());
        temp.setAmount(expensesMainDto.getAmount());
        //temp.setPaymentDate(salaryDto.getPaymentDate());

        expensesMainServiceImpl.update(temp);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }
    @PutMapping("/expenses/amount")
    public ResponseEntity<String> updateAmount(@Valid @RequestBody UpdatePayload updatePayload){
        ExpensesMainDto expensesMainDto = expensesMainServiceImpl.findByEmpId(updatePayload.getEmpId());
        long total = expensesMainDto.getAmount()+updatePayload.getAmount();
        String s = "Waiting for Payment";
        String m =expensesMainServiceImpl.amountUpdate(updatePayload.getEmpId(),total,s);
        return ResponseEntity.ok(m);
    }

    @PutMapping("/admin/expenses/approve/{empId}")
    public ResponseEntity<String> adminProveExpenses(@PathVariable long empId){
        ExpensesMainDto expensesMainDto = expensesMainServiceImpl.findByEmpId(empId);
        //System.out.println(expensesMainDto.getStatus().equals("Payment Done"));
        if(!expensesMainDto.getStatus().equals("Payment Done")){
            SalaryDto salaryDto = salaryServiceImpl.findByEmpId(empId);
            long totalAmount = salaryDto.getTotalExpensesAmount();
            long amount = expensesMainDto.getAmount();
            long temp = totalAmount+amount;
            SalaryNotificationDto salaryNotificationDto = new SalaryNotificationDto();
            String description =" "+expensesMainDto.getEmpName()+" Your Approved Expenses"+"  Amount "+expensesMainDto.getAmount()+" Was credit in Your Bank Account NUmber: "+expensesMainDto.getAccountNumber();
            salaryNotificationDto.setEmpId(empId);
            salaryNotificationDto.setDescription(description);
            String m1 = salaryServiceImpl.salaryNotificationAdd(salaryNotificationDto);
            String msg = salaryServiceImpl.totalExpensesAmountUpdate(empId,temp);
            String s = "Payment Done";
            expensesMainServiceImpl.amountUpdate(empId,0,s);
        }
        else {
                throw new AlreadyApproveException();
        }
        return ResponseEntity.ok("Payment Done");
    }
    @PutMapping("/admin/salary/credit/{empId}")
    public ResponseEntity<String> adminSalaryCredit(@PathVariable long empId){
        SalaryDto salaryDto = salaryServiceImpl.findByEmpId(empId);
        long salary = salaryDto.getMonthAmount();
        LocalDateTime dateTime = LocalDateTime.now();
        long totalSalary = salaryDto.getTotalAmount();
        long temp = totalSalary+salary;
        String msg = salaryServiceImpl.totalPaymentDate(empId,dateTime,temp);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<SalaryDto> GetEmployeeForSalary(@PathVariable long empId){
        SalaryDto salaryDto = salaryServiceImpl.findByEmpId(empId);
        if(salaryDto==null){
            throw new ResourceNotFoundException();
        }
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Employee get by employee id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto);
    }

    @GetMapping("/notification/{empId}")
    public ResponseEntity<List<SalaryNotificationDto>> allSalaryNotificationByEmpId(@PathVariable long empId){
        List<SalaryNotificationDto> temp = salaryServiceImpl.findByIdEmployeeIdOpenFeign(empId);
        return ResponseEntity.ok(temp);
    }
}
