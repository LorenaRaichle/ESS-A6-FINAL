package com.example.HospitalAppointmentManager.repository;

import com.example.HospitalAppointmentManager.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findById (long id);
}
