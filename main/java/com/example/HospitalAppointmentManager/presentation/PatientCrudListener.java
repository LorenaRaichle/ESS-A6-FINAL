package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Patient;
import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import com.example.HospitalAppointmentManager.service.PatientService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@SpringComponent
public class PatientCrudListener implements CrudListener {

    private final PatientService patientService;
    private final AppointmentRepository appointmentRepository;
    public PatientCrudListener(PatientService patientService, AppointmentRepository appointmentRepository) {
        this.patientService = patientService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Collection findAll() {
        List<Patient> patients = patientService.findAll();

        for (Patient p : patients) {
            p.setAppointment_ID(appointmentRepository.findByPatientID(p.getId()));
        }
        return patients;
    }

    @Override
    public Object add(Object o) {
        Patient p = (Patient)o;
        patientService.add(p);
        return p;
    }

    @Override
    public Object update(Object o) {
        Patient p = (Patient)o;
        patientService.update(p);
        return p;
    }

    @Override
    public void delete(Object o) {
        Patient p = (Patient)o;
        patientService.deleteById(p.getId());
    }
}
