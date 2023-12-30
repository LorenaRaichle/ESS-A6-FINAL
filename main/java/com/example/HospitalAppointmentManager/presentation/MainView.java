package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import com.example.HospitalAppointmentManager.service.DoctorService;
import com.example.HospitalAppointmentManager.service.PatientService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("main")
@PageTitle("Main | HospitalAppointmentManager")
public class MainView extends VerticalLayout {

    public MainView(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, AppointmentRepository appointmentRepository) {
        setSizeFull();

        AppointmentView appointmentView = new AppointmentView(appointmentService, doctorService, patientService);
        DoctorView doctorView = new DoctorView(doctorService, appointmentService, appointmentRepository);
        PatientView patientView = new PatientView(patientService, appointmentService, appointmentRepository);


        Tab appointment = new Tab("Appointment");
        Tab patient = new Tab("Patient");
        Tab doctor = new Tab("Doctor");
        Tabs tabs = new Tabs(doctor, patient, appointment);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        add(tabs);
        add(doctorView);

        tabs.addSelectedChangeListener(e -> {
                    removeAll();
                    add(tabs);
                    if (e.getSelectedTab().equals(doctor)) {
                        add(doctorView);
                    } else if (e.getSelectedTab().equals(patient)) {
                        add(patientView);
                    } else if (e.getSelectedTab().equals(appointment)) {
                        add(appointmentView);
                    }
                }
        );
    }
}

