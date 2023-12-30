package com.example.HospitalAppointmentManager.repository;

import com.example.HospitalAppointmentManager.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient findById(long id);
}
