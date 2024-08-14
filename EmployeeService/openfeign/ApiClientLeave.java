package com.employeeManagement.employeeService.openfeign;


import com.employeeManagement.employeeService.dto.LeavesDto;
import com.employeeManagement.employeeService.dto.TotalLeavesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "LEAVES-SERVICE")
@Configuration
public interface ApiClientLeave {
    @RequestMapping(method = RequestMethod.GET,value = "/leaves/{id}")
    public LeavesDto findByIdForLeavesOpenFeign(@PathVariable long id);
    @RequestMapping(method = RequestMethod.GET,value = "/leaves/list/{employeeId}")
    public List<LeavesDto> findByIdEmployeeIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.GET,value = "/leaves/pending/list/{employeeId}")
    public List<LeavesDto> findByIdEmployeeIdWithStatusOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/leaves/request", consumes = "application/json")
    public LeavesDto createLeavesByEmployee(LeavesDto leavesDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/leaves/edit", consumes = "application/json")
    public LeavesDto editLeavesOpenFeign(LeavesDto leavesDto);

    @RequestMapping(method = RequestMethod.DELETE,value = "/leaves/{id}")
    public String deleteLeavesById(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/leaves/total/employee/{employeeId}")
    public TotalLeavesDto totalLeavesId(@PathVariable long employeeId);
}
