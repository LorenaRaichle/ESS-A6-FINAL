package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.model.Doctor;
import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import com.example.HospitalAppointmentManager.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;

@Route("doctor")
public class DoctorView extends VerticalLayout {
    private DoctorService doctorService;
    private GridCrud<Doctor> doctorGrid = new GridCrud<>(Doctor.class);
    private AppointmentRepository appointmentRepository;

    private AppointmentService appointmentService;


    public DoctorView (DoctorService doctorService, AppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.doctorService = doctorService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;

        Div notificationBox = new Div();
        notificationBox.getStyle().set("background-color", "#f0f0f0");
        notificationBox.getStyle().set("padding", "10px");
        notificationBox.getStyle().set("border-radius", "5px");
        notificationBox.getStyle().set("margin", "10px");
        notificationBox.getStyle().set("width", "100%");

        Span notificationText = new Span("Please note that doctors and patients can only be deleted if they are currently not registered for any appointment.");
        notificationBox.add(notificationText);

        add(notificationBox);

        addClassName("doctor-list");

        configureGrid();

        TextField searchField = new TextField();
        searchField.setWidth("19%");
        searchField.setPlaceholder("Search...");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        searchField.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase().trim();
            doctorGrid.getGrid().setItems(doctorService.filterDoctors(filterText));
        });

        add(searchField, doctorGrid);
    }
    public void configureGrid() {

        doctorGrid.setCrudListener(new DoctorCrudListener(doctorService, appointmentRepository));
        doctorGrid.addClassName("doctor-grid");
        doctorGrid.setSizeFull();
        doctorGrid.getGrid().setColumns("id", "firstName", "lastName", "email", "areaOfExpertise", "appointment_ID");

        Grid<Doctor> grid = doctorGrid.getGrid();
        grid.addColumn(new ComponentRenderer<>(doctor -> {
            Button detailsButton = new Button("Appointment Details", new Icon(VaadinIcon.ANGLE_RIGHT));
            detailsButton.addClickListener(e -> {
                openAppointmentDetails(doctor);
            });
            return detailsButton;
        })).setHeader("Information");

        doctorGrid.setDeleteOperation(doctor -> {
            // Check if the doctor has ever had an appointment
            if (!appointmentRepository.findByDoctorID(doctor.getId()).isEmpty()) {
                Notification.show("This doctor cannot be deleted because there are registered appointments.",
                        5000,
                        Notification.Position.MIDDLE);
            } else {
                // If there were no appointments, proceed with deletion
                try {
                    doctorService.deleteById(doctor.getId());
                    doctorGrid.refreshGrid();
                    Notification.show("Doctor deleted successfully.",
                            3000,
                            Notification.Position.BOTTOM_START);
                } catch (Exception e) {
                    // Handle any exception during deletion
                    Notification.show("Error during deletion: " + e.getMessage(),
                            5000,
                            Notification.Position.MIDDLE);
                }
            }
        });


    }

    private void openAppointmentDetails(Doctor doctor) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctorId(doctor.getId());

        Dialog detailsDialog = new Dialog();
        detailsDialog.add(new H3("Appointment Dates for doctor " + doctor.getFirstName() + " " + doctor.getLastName()));

        appointments.stream()
                .map(appointment -> new Paragraph(appointment.getDate().toString() + " with patient " + appointment.getPatientName()))
                .forEach(detailsDialog::add);

        detailsDialog.open();


    }

}

