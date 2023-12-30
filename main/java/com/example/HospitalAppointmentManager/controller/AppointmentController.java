package com.example.HospitalAppointmentManager.controller;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity add(@RequestBody Appointment appointment) {
        appointmentService.add(appointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addall", consumes = "application/json")
    public ResponseEntity addAll(@RequestBody ArrayList<Appointment> appointments) {
        for (Appointment a : appointments) {
            appointmentService.add(a);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/findall", produces = "application/json")
    public ArrayList<Appointment> getAll() {
        return appointmentService.findAll();
    }

    @GetMapping(value = "/findbynumber/{number}", produces = "application/json")
    public Appointment getById(@PathVariable long number) {
        return appointmentService.findById(number);
    }

    @PutMapping(value = "updateappointment/{number}", consumes = "application/json")
    public ResponseEntity update(@PathVariable long number, @RequestBody Appointment appointment) {
        if (number == appointment.getId()) {
            appointmentService.update(appointment);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("deletebynumber/{number}")
    public ResponseEntity deleteByNumber (@PathVariable long number) {
        appointmentService.deleteById(number);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll(@PathVariable long number) {
        appointmentService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
