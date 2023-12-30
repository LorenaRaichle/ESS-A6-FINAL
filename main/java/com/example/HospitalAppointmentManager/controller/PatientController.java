package com.example.HospitalAppointmentManager.controller;

import com.example.HospitalAppointmentManager.model.Patient;
import com.example.HospitalAppointmentManager.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity add(@RequestBody Patient patient) {
        patientService.add(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addall", consumes = "application/json")
    public ResponseEntity addAll(@RequestBody ArrayList<Patient> patients) {
        for (Patient p : patients) {
            patientService.add(p);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/findall", produces = "application/json")
    public ArrayList<Patient> getAll() {
        return patientService.findAll();
    }

    @GetMapping(value = "/findbynumber/{number}", produces = "application/json")
    public Patient getById(@PathVariable long number) {
        return patientService.findById(number);
    }

    @PutMapping(value = "updatepatient/{number}", consumes = "application/json")
    public ResponseEntity update(@PathVariable long number, @RequestBody Patient patient) {
        if (number == patient.getId()) {
            patientService.update(patient);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("deletebynumber/{number}")
    public ResponseEntity deleteByNumber (@PathVariable long number) {
        patientService.deleteById(number);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll() {
        patientService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
