package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.model.Doctor;
import com.example.HospitalAppointmentManager.model.Patient;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import com.example.HospitalAppointmentManager.service.DoctorService;
import com.example.HospitalAppointmentManager.service.PatientService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@SpringComponent
public class AppointmentCrudListener implements CrudListener {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentCrudListener(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @Override
    public Collection findAll() {
        return appointmentService.findAll();
    }

    @Override
    public Object add(Object o) {
        Appointment a = (Appointment) o;

        Patient p = patientService.findById(a.getPatientID());
        Doctor d = doctorService.findById(a.getDoctorID());

        if (d != null && p != null) {
            a.setPatient(p);
            a.setPatientName(p.getFirstName() + " " + p.getLastName());

            a.setDoctor(d);
            a.setDoctorName(d.getFirstName() + " " + d.getLastName());

            appointmentService.add(a);
            return a;
        }
        return null;
    }
    @Override
    public Object update(Object o) {
        Appointment updatedAppointment = (Appointment) o;

        Doctor d = doctorService.findById(updatedAppointment.getDoctorID());
        updatedAppointment.setDoctor(d);
        updatedAppointment.setDoctorName(d.getFirstName() + " " + d.getLastName());

        Patient p = patientService.findById(updatedAppointment.getPatientID());
        updatedAppointment.setPatient(p);
        updatedAppointment.setPatientName(p.getFirstName() + " " + p.getLastName());

        appointmentService.update(updatedAppointment);
        return updatedAppointment;
    }

    @Override
    public void delete(Object o) {
        Appointment d = (Appointment)o;
        appointmentService.delete(d);
    }
}