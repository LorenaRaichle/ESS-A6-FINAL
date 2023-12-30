package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import com.example.HospitalAppointmentManager.service.DoctorService;
import com.example.HospitalAppointmentManager.service.PatientService;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("Appointment")
public class AppointmentView extends VerticalLayout {

    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private GridCrud<Appointment> appointmentGrid = new GridCrud<>(Appointment.class);

    public AppointmentView (AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;

        addClassName("list-view");


        configureGrid();

        TextField searchField = new TextField();
        searchField.setWidth("19%");
        searchField.setPlaceholder("Search...");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        searchField.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase().trim();
            appointmentGrid.getGrid().setItems(appointmentService.filterAppointments(filterText));
        });

        add(searchField, appointmentGrid);
    }
    public void configureGrid() {
        appointmentGrid.setCrudListener(new AppointmentCrudListener(appointmentService, doctorService, patientService));
        appointmentGrid.addClassName("appointment-grid");
        appointmentGrid.setSizeFull();

        appointmentGrid.getGrid().setColumns("id", "date", "appointmentStatus", "room");
        appointmentGrid.getGrid().addColumn(Appointment::getDoctorName).setHeader("Doctor");
        appointmentGrid.getGrid().addColumn(Appointment:: getPatientName).setHeader("Patient");
        appointmentGrid.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
    }
}
