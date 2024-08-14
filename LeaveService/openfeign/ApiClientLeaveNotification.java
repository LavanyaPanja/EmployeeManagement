package com.employeeManagement.leavesService.openfeign;

import com.employeeManagement.leavesService.dto.LeaveNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "NOTIFICATION-SERVICE")
@Configuration
public interface ApiClientLeaveNotification {
    @RequestMapping(method = RequestMethod.GET,value = "/notification/employee/{employeeId}")
    public List<LeaveNotificationDto> findByIdEmployeeIdOpenFeign(@PathVariable long employeeId);

    @RequestMapping(method = RequestMethod.POST, value = "/notification/create", consumes = "application/json")
    public LeaveNotificationDto leaveNotificationAdd(LeaveNotificationDto leaveNotificationDto);
}
