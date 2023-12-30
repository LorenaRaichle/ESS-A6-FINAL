package com.example.HospitalAppointmentManager.repository;

import com.example.HospitalAppointmentManager.model.Appointment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    Appointment findById (long id);

    List<Appointment> findByDoctorID(long doctorId);
    List<Appointment> findByPatientID (long patientId);




}
