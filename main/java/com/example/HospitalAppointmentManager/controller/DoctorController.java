package com.example.HospitalAppointmentManager.controller;

import com.example.HospitalAppointmentManager.model.Doctor;
import com.example.HospitalAppointmentManager.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity add(@RequestBody Doctor doctor) {
        doctorService.add(doctor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addall", consumes = "application/json")
    public ResponseEntity addAll(@RequestBody ArrayList<Doctor> doctors) {
        for (Doctor d : doctors) {
            doctorService.add(d);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/findall", produces = "application/json")
    public ArrayList<Doctor> getAll() {
        return doctorService.findAll();
    }

    @GetMapping(value = "/findbynumber/{number}", produces = "application/json")
    public Doctor getById(@PathVariable long number) {
        return doctorService.findById(number);
    }

    @PutMapping(value = "updatedoctor/{number}", consumes = "application/json")
    public ResponseEntity update(@PathVariable long number, @RequestBody Doctor doctor) {
        if (number == doctor.getId()) {
            doctorService.update(doctor);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("deletebynumber/{number}")
    public ResponseEntity deleteByNumber (@PathVariable long number) {
        doctorService.deleteById(number);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll(@PathVariable long number) {
        doctorService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
