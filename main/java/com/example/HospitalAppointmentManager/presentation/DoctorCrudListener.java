package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Doctor;
import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import com.example.HospitalAppointmentManager.service.DoctorService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@SpringComponent
public class DoctorCrudListener implements CrudListener {

    private final DoctorService doctorService;

    private final AppointmentRepository appointmentRepository;

    public DoctorCrudListener(DoctorService doctorService, AppointmentRepository appointmentRepository) {
        this.doctorService = doctorService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Collection findAll() {
        List<Doctor> doctors = doctorService.findAll();

        for (Doctor d : doctors) {
            d.setAppointment_ID(appointmentRepository.findByDoctorID(d.getId()));
        }

        return doctors;
    }

    @Override
    public Object add(Object o) {
        Doctor d = (Doctor) o;
        doctorService.add(d);
        return d;
    }

    @Override
    public Object update(Object o) {
        Doctor d = (Doctor) o;
        doctorService.update(d);
        return d;
    }

    @Override
    public void delete(Object o) {
        Doctor d = (Doctor) o;
        doctorService.deleteById(d.getId());
    }
}
