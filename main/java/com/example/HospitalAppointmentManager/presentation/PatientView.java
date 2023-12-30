package com.example.HospitalAppointmentManager.presentation;

import com.example.HospitalAppointmentManager.model.Appointment;
import com.example.HospitalAppointmentManager.model.Patient;
import com.example.HospitalAppointmentManager.repository.AppointmentRepository;
import com.example.HospitalAppointmentManager.service.AppointmentService;
import com.example.HospitalAppointmentManager.service.PatientService;
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

@Route("patient")
public class PatientView extends VerticalLayout {
    private PatientService patientService;
    private AppointmentRepository appointmentRepository;
    private GridCrud<Patient> patientGrid = new GridCrud<>(Patient.class);

    private AppointmentService appointmentService;


    public PatientView (PatientService patientService, AppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.patientService = patientService;
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
        addClassName("patient-list");

        configureGrid();

        TextField searchField = new TextField();
        searchField.setWidth("19%");
        searchField.setPlaceholder("Search...");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        searchField.addValueChangeListener(event -> {
            String filterText = event.getValue().toLowerCase().trim();
            patientGrid.getGrid().setItems(patientService.filterPatients(filterText));
        });

        add(searchField, patientGrid);
    }
    public void configureGrid() {
        patientGrid.setCrudListener(new PatientCrudListener(patientService, appointmentRepository));
        patientGrid.addClassName("patient-grid");
        patientGrid.setSizeFull();
        patientGrid.getGrid().setColumns("id", "firstName", "lastName", "email", "treatment", "appointment_ID");


        Grid<Patient> grid = patientGrid.getGrid();
        grid.addColumn(new ComponentRenderer<>(patient -> {
            Button detailsButton = new Button("Appointment Details", new Icon(VaadinIcon.ANGLE_RIGHT));
            detailsButton.addClickListener(e -> {
                openAppointmentDetails(patient);
            });
            return detailsButton;
        })).setHeader("Information");

        patientGrid.setDeleteOperation(patient -> {
            // Check if the doctor has ever had an appointment
            if (!appointmentRepository.findByPatientID(patient.getId()).isEmpty()) {
                // If there were appointments, show an informational message
                Notification.show("This patient cannot be deleted because there are registered appointments.",
                        5000,
                        Notification.Position.MIDDLE);
            } else {
                // If there were no appointments, proceed with deletion
                try {
                    patientService.deleteById(patient.getId());
                    patientGrid.refreshGrid();
                    Notification.show("Patient deleted successfully.",
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

    private void openAppointmentDetails(Patient patient) {
        List<Appointment> appointments = appointmentService.findAppointmentsByPatientId(patient.getId());

        Dialog detailsDialog = new Dialog();
        detailsDialog.add(new H3("Appointment Dates for patient " + patient.getFirstName() + " " + patient.getLastName()));

        appointments.stream()
                .map(appointment -> new Paragraph(appointment.getDate().toString() + " with doctor " + appointment.getDoctorName()))
                .forEach(detailsDialog::add);

        detailsDialog.open();


    }
}
