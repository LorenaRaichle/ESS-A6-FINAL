package com.example.HospitalAppointmentManager.service;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AppointmentService implements ObjectService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public ArrayList<Appointment> findAll() {
        ArrayList<Appointment> output = new ArrayList<>();
        appointmentRepository.findAll().forEach(output::add);
        return output;
    }
    @Override
    public Appointment findById(long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public void add(Object o) {
        Appointment a = (Appointment) o;
        appointmentRepository.save(a);
    }

    @Override
    public void update(Object o) {
        Appointment a = (Appointment) o;
        appointmentRepository.save(a);
    }

    @Override
    public void delete(Object o) {
        Appointment a = (Appointment) o;
        appointmentRepository.delete(a);
    }

    @Override
    public void deleteById(long id) {
        Appointment a = appointmentRepository.findById(id);
        appointmentRepository.delete(a);
    }

    @Override
    public void deleteAll() {
        appointmentRepository.deleteAll();
    }


    public List<Appointment> filterAppointments(String filterText) {
        List<Appointment> appointments = findAll(); // Fetch the appointments each time
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentStatus().toString().toLowerCase().contains(filterText.toLowerCase())
                        || appointment.getDate().toString().toLowerCase().contains(filterText.toLowerCase())
                        || (appointment.getPatient() != null && appointment.getPatientName().toString().toLowerCase().contains(filterText.toLowerCase()))
                        || (appointment.getRoom() != null && appointment.getRoom().toString().toLowerCase().contains(filterText.toLowerCase()))
                        || (appointment.getDoctor() != null && appointment.getDoctorName().toString().toLowerCase().contains(filterText.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAppointmentsByDoctorId(long doctorId) {
        return appointmentRepository.findByDoctorID(doctorId);
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAppointmentsByPatientId(long patientId) {
        return appointmentRepository.findByPatientID(patientId);
    }


}
