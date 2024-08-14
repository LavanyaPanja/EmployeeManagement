package com.employeeManagement.securityService.openfeign;

import com.employeeManagement.securityService.dto.ExpensesDto;
import com.employeeManagement.securityService.dto.LeavesDto;
import com.employeeManagement.securityService.dto.RequestDto;
import com.employeeManagement.securityService.dto.RequestLeavesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "LEAVES-SERVICE")
@Configuration
public interface ApiClientLeaves {

    @RequestMapping(method = RequestMethod.PUT, value = "/leaves/approve", consumes = "application/json")
    public String leavesApprove(RequestLeavesDto requestDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/leaves/reject", consumes = "application/json")
    public String leavesReject(RequestLeavesDto requestDto);

    @RequestMapping(method = RequestMethod.GET,value = "/leaves/manager/pending/list/{managerId}")
    public List<LeavesDto> findPendingRequestByManager(@PathVariable long managerId);



}
