package com.employeeManagement.securityService.controller;

import com.employeeManagement.securityService.config.JwtGeneratorValidator;
import com.employeeManagement.securityService.dto.*;
import com.employeeManagement.securityService.entity.User;
import com.employeeManagement.securityService.exception.*;
import com.employeeManagement.securityService.repository.UserRepository;
import com.employeeManagement.securityService.service.DefaultUserService;
import com.employeeManagement.securityService.service.impl.AdminSecurityServiceImpl;
import com.employeeManagement.securityService.service.impl.EmployeeSecurityServiceImpl;
import com.employeeManagement.securityService.service.impl.HRSecurityServiceImpl;
import com.employeeManagement.securityService.service.impl.ManagerSecurityServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestAppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    @Autowired
    private BCryptPasswordEncoder bcCryptPasswordEncoder;

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private EmployeeSecurityServiceImpl employeeSecurityServiceImpl;

    @Autowired
    private HRSecurityServiceImpl hrSecurityServiceImpl;
    @Autowired
    private ManagerSecurityServiceImpl managerSecurityServiceImpl;
    @Autowired
    private AdminSecurityServiceImpl adminSecurityServiceImpl;

    @PostMapping("/security/registration")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDto) {
        User users =  userService.save(userDto);
        if (users.equals(null))
            return generateResponse("Not able to save user ", HttpStatus.BAD_REQUEST, userDto);
        else
            return generateResponse("User saved successfully : " + users.getId(), HttpStatus.OK, users);
    }

    @GetMapping("/security/genToken")
    public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenVal.generateToken(authentication);
    }


    @GetMapping("/welcomeAdmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcome() {
        return "Welcome Admin";
    }

    @GetMapping("/welcomeUser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String welcomeUser() {
        return "Welcome USER";
    }
    @GetMapping("/welcomeManager")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public String welcomeManager() {
        return "Welcome MANAGER";
    }

    //employee apis with security as role USER

    @GetMapping("/security/employee/{empId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<EmployeeDto> getEmployeeWithId(@PathVariable long empId){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(empId);

            if (employeeDto == null) {
                throw new ResourceNotFoundException();
            }
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Employee get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(employeeDto);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PostMapping("/security/employee/create")
    public ResponseEntity<EmployeeDto> employeeCreated(@Valid @RequestBody EmployeeDto employeeDto){
            EmployeeDto employeeDto1 = employeeSecurityServiceImpl.createEmployeeByHR(employeeDto);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Employee get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(employeeDto1);
    }
    @GetMapping("/security/employee/list/{hrId}")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<List<EmployeeDto>> allEmployeeByHr(@PathVariable long hrId){
        List<EmployeeDto> temp = employeeSecurityServiceImpl.allListEmployeeByHr(hrId);
        if(temp.size()==0){
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/security/manager/employee/list/{managerId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<EmployeeDto>> allEmployeeByManager(@PathVariable long managerId){
        List<EmployeeDto> temp = employeeSecurityServiceImpl.allListEmployeeByManager(managerId);
        if(temp.size()==0){
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok(temp);
    }
    @DeleteMapping("/security/employee/{id}")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<String> deleteEmployee(@Valid @PathVariable long id){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(id);
            if (employeeDto == null) {
                throw new ResourceNotFoundException();
            }
            String message = employeeSecurityServiceImpl.deleteEmployeeById(id);
            return ResponseEntity.ok(message);
        }
        catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    //expenses api with security

    @GetMapping("/security/employee/expenses/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ExpensesDto>> getExpensesWithId(@PathVariable long employeeId){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(employeeId);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            List<ExpensesDto> expensesDto = employeeSecurityServiceImpl.findByIdExpensesIdOpenFeign(employeeId);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Expenses get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/security/employee/expenses/pending/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ExpensesDto>> getExpensesWithStatusId(@PathVariable long employeeId){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(employeeId);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            List<ExpensesDto> expensesDto = employeeSecurityServiceImpl.findByIdExpensesIdWithStatusOpenFeign(employeeId);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Expenses get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/security/employee/test/expenses/{expensesId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<ExpensesDto> getExpensesWithExpensesId(@PathVariable long expensesId){
        try {
            ExpensesDto expensesDto = employeeSecurityServiceImpl.findByIdForExpensesOpenFeign(expensesId);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Expenses get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
        }catch (Exception ex){
            throw new ExpensesNotFoundException();
        }

    }


    @PostMapping("/security/employee/expenses/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> expensesCreated(@Valid @RequestBody ExpensesDto expensesDto){
        String expensesDto1 = employeeSecurityServiceImpl.createExpensesByEmployee(expensesDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses created by employee in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto1);
    }

    @DeleteMapping("security/employee/expenses/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> deleteExpenses(@Valid @PathVariable long id){

        try {
            ExpensesDto expensesDto = employeeSecurityServiceImpl.findByIdForExpensesOpenFeign(id);
            if(expensesDto==null){
                throw new ResourceNotFoundException();
            }
            String message = employeeSecurityServiceImpl.deleteExpensesById(id);
            return ResponseEntity.ok(message);
        }catch (Exception ex){
            throw new ExpensesNotFoundException();
        }
    }

    // leaves
    @GetMapping("/security/employee/test/leaves/{leavesID}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<LeavesDto> getLeavesWithExpensesId(@PathVariable long leavesID){
       try{
           LeavesDto leavesDto = employeeSecurityServiceImpl.findByIdForLeavesOpenFeign(leavesID);
           HttpHeaders header = new HttpHeaders();
           header.add("desc","Leaves get by expenses id in security service");
           return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
       }catch (Exception ex){
           throw new NoDateFoundException();
       }
    }

    @GetMapping("/security/employee/leaves/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<LeavesDto>> getLeavesWithId(@PathVariable long employeeId){
       try {
           EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(employeeId);
           if(employeeDto==null){
               throw new ResourceNotFoundException();
           }
           List<LeavesDto> leavesDtos = employeeSecurityServiceImpl.findByIdLeavesIdOpenFeign(employeeId);
           HttpHeaders header = new HttpHeaders();
           header.add("desc","Expenses get by employee id in security service");
           return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDtos);
       }catch (Exception ex){
           throw new ResourceNotFoundException();
       }
    }

    @GetMapping("/security/employee/leaves/pending/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<LeavesDto>> getLeavesWithStatusId(@PathVariable long employeeId){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(employeeId);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            List<LeavesDto> leavesDtos = employeeSecurityServiceImpl.findByIdLeavesIdWithStatusOpenFeign(employeeId);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Leaves get by employee id in security service");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDtos);
        }catch (Exception ex){
            throw new NoDateFoundException();
        }
    }

    @PostMapping("/security/employee/leaves/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> leavesCreated(@Valid @RequestBody LeavesDto leavesDto){
        String leavesDto1 = employeeSecurityServiceImpl.createLeavesByEmployee(leavesDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves created by employee in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto1);
    }

    @DeleteMapping("security/employee/leaves/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> deleteLeaves(@Valid @PathVariable long id){
        try {
            LeavesDto leavesDto = employeeSecurityServiceImpl.findByIdForLeavesOpenFeign(id);
            if(leavesDto==null){
                throw new ResourceNotFoundException();
            }
            String message = employeeSecurityServiceImpl.deleteLeavesById(id);
            return ResponseEntity.ok(message);
        }catch (Exception ex){
            throw new NoRequestFoundException();
        }
    }
    @PutMapping("/security/employee/leaves/edit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> editLeaves(@Valid @RequestBody LeavesDto leavesDto){
        String leavesDto1 = employeeSecurityServiceImpl.editLeavesOpenFeign(leavesDto);
        String m = "Leave Successfully edited";
        return ResponseEntity.ok(m);
    }

    @GetMapping("/security/employee/total/leaves/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<TotalLeavesDto> totalLeaves(@PathVariable long id){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(id);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            TotalLeavesDto totalLeavesDto = employeeSecurityServiceImpl.totalLeavesId(id);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Expenses get by employee id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(totalLeavesDto);
        }catch (Exception ex){
            throw new NoDateFoundException();
        }
    }

    @GetMapping("/security/employee/salary/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SalaryDto> getSalary(@PathVariable long id){
        try {
            SalaryDto salaryDto = employeeSecurityServiceImpl.findByIdForSalaryOpenFeign(id);
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
    @PutMapping("/security/hr/expenses/approve")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<String> approveExpenses(@Valid @RequestBody RequestDto requestDto){
        String m = hrSecurityServiceImpl.expensesApprove(requestDto);
        return ResponseEntity.ok(m);
    }
    @PutMapping("/security/hr/expenses/reject")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<String> rejectExpenses(@Valid @RequestBody RequestDto requestDto){
        String m = hrSecurityServiceImpl.expensesReject(requestDto);
        return ResponseEntity.ok(m);
    }
    @GetMapping("/security/hr/expenses/pending/{hrId}")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<List<ExpensesDto>> getExpensesWithHrId(@PathVariable long hrId){
       List<ExpensesDto> expensesDto = hrSecurityServiceImpl.findExpensesByHrIdOpenFeign(hrId);
       if(expensesDto.size()==0){
           throw new ExpensesNotFoundException();
       }
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Expenses get by HR id in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesDto);
    }

    //approve and reject for leaves by managers
    @PutMapping("/security/manager/leaves/approve")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<String> approveLeaves(@Valid @RequestBody RequestLeavesDto requestDto){
        String m = managerSecurityServiceImpl.leavesApprove(requestDto);
        return ResponseEntity.ok(m);
    }
    @PutMapping("/security/manager/leaves/reject")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<String> rejectLeaves(@Valid @RequestBody RequestLeavesDto requestDto){
        String m = managerSecurityServiceImpl.leavesReject(requestDto);
        return ResponseEntity.ok(m);
    }

    @GetMapping("/security/manager/leaves/pending/{managerId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<LeavesDto>> getLeavesWithManagerId(@PathVariable long managerId){
        List<LeavesDto> leavesDto = managerSecurityServiceImpl.findPendingRequestByManager(managerId);
        System.out.println(leavesDto.size());
        if(leavesDto.size()==0){
            throw new NoDateFoundException();
        }
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Leaves get by Manager id in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(leavesDto);
        //return ResponseEntity.ok(leavesDto);
    }
    /*@GetMapping("/security/manager/leaves/pending/test/{managerId}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<LeavesDto>> pendingLeaveList(@PathVariable long managerId){
        EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(managerId);
        if(employeeDto==null){
            throw new ResourceNotFoundException();
        }
        List<LeavesDto> leavesDtos = managerSecurityServiceImpl.findPendingRequestByManager(managerId);
        return ResponseEntity.ok(leavesDtos);
    }*/
    //admin apis for salary with security
    @GetMapping("/security/admin/salary/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<SalaryDto>> getAllSalaryAccount(){
        List<SalaryDto> salaryDto = adminSecurityServiceImpl.findAllSalaryAccounts();
        HttpHeaders header = new HttpHeaders();
        header.add("desc","All Salary Account in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto);
    }
    @DeleteMapping("/security/admin/salary/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteSalaryAccount(@Valid @PathVariable long id){
        try {
            SalaryDto salaryDto = adminSecurityServiceImpl.findByAccountId(id);
            if(salaryDto==null){
                throw new ResourceNotFoundException();
            }
            String message = adminSecurityServiceImpl.deleteSalaryAccount(id);
            return ResponseEntity.ok(message);
        }catch (Exception ex){
            throw new SalaryNotFoundException();
        }
    }
    @PostMapping("/security/admin/salary/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SalaryDto> salaryCreate(@Valid @RequestBody SalaryDto salaryDto){
        SalaryDto salaryDto1 = adminSecurityServiceImpl.createSalaryAccount(salaryDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","salary account created by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto1);
    }

    @PutMapping("/security/admin/salary/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SalaryDto> updateSalary(@Valid @RequestBody SalaryDto salaryDto){
        SalaryDto salaryDto1 = adminSecurityServiceImpl.updateSalaryAccount(salaryDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","salary account update by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto1);
    }
    //admin apis for expenses with security
    @GetMapping("/security/admin/expenses/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ExpensesMainDto>> getAllExpensesAccount(){
        List<ExpensesMainDto> expensesMainDto = adminSecurityServiceImpl.findAllExpensesAccounts();
        HttpHeaders header = new HttpHeaders();
        header.add("desc","All Expenses Account in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesMainDto);
    }
    @DeleteMapping("/security/admin/expenses/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteExpensesAccount(@Valid @PathVariable long id){

        ExpensesMainDto expensesMainDto = adminSecurityServiceImpl.findExpenseById(id);
        if(expensesMainDto==null){
            throw new ResourceNotFoundException();
        }
        String message = adminSecurityServiceImpl.deleteExpensesAccount(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/security/admin/expenses/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExpensesMainDto> expensesCreate(@Valid @RequestBody ExpensesMainDto expensesMainDto){
        ExpensesMainDto expensesMainDto1 = adminSecurityServiceImpl.createExpensesAccount(expensesMainDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","expenses account created by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesMainDto1);
    }

    @PutMapping("/security/admin/expenses/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExpensesMainDto> updateExpenses(@Valid @RequestBody ExpensesMainDto expensesMainDto){
        ExpensesMainDto salaryDto1 = adminSecurityServiceImpl.updateExpensesAccount(expensesMainDto);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","expenses account update by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto1);
    }

    @PutMapping("/security/admin/expenses/approve/{empId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> expenseApproveByAdmin(@PathVariable long empId){
        String salaryDto1 = adminSecurityServiceImpl.expensesApproveByAdmin(empId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","expenses amount credit update by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto1);
    }

    @PutMapping("/security/admin/salary/approve/{empId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> salaryCredit(@PathVariable long empId){
        String salaryDto1 = adminSecurityServiceImpl.salaryCredit(empId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","salary amount credit update by admin in security service");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryDto1);
    }
    @GetMapping("/security/leave/notification/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<LeaveNotificationDto>> leavesNotification(@PathVariable long id){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(id);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            List<LeaveNotificationDto> leaveNotificationDto = employeeSecurityServiceImpl.leavesNotification(id);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Leave Notification get by employee id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(leaveNotificationDto);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @GetMapping("/security/expenses/notification/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ExpensesNotificationDto>> expensesNotification(@PathVariable long id){
        try {
            EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(id);
            if(employeeDto==null){
                throw new ResourceNotFoundException();
            }
            List<ExpensesNotificationDto> expensesNotification = employeeSecurityServiceImpl.expensesNotification(id);
            HttpHeaders header = new HttpHeaders();
            header.add("desc","Expenses Notification get by employee id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(expensesNotification);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @GetMapping("/security/salary/notification/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<SalaryNotificationDto>> salaryNotification(@PathVariable long id){
       try {
           EmployeeDto employeeDto = employeeSecurityServiceImpl.findByIdForEmployeeOpenFeign(id);
           if(employeeDto==null){
               throw new ResourceNotFoundException();
           }
           List<SalaryNotificationDto> salaryNotification = employeeSecurityServiceImpl.salaryNotification(id);
           HttpHeaders header = new HttpHeaders();
           header.add("desc","Leave Notification get by employee id");
           return ResponseEntity.status(HttpStatus.OK).headers(header).body(salaryNotification);
       }catch (Exception ex){
           throw new ResourceNotFoundException();
       }
    }










    public ResponseEntity<Object> generateResponse(String message, HttpStatus st, Object responseObj) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("Status", st.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, st);
    }

}
