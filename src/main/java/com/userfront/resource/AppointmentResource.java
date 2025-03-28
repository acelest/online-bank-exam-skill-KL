package com.userfront.resource;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
@PreAuthorize("hasRole('ADMIN')")
@Api(value = "Appointment Management", description = "Operations pertaining to appointments in Online Banking System")
public class AppointmentResource {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation(value = "View a list of all appointments", response = List.class)
    @RequestMapping("/all")
    public List<Appointment> findAppointmentList() {
        List<Appointment> appointmentList = appointmentService.findAll();

        return appointmentList;
    }

    @ApiOperation(value = "Confirm an appointment")
    @RequestMapping("/{id}/confirm")
    public void confirmAppointment(
            @ApiParam(value = "Appointment id to confirm", required = true)
            @PathVariable("id") Long id) {
        appointmentService.confirmAppointment(id);
    }
}
