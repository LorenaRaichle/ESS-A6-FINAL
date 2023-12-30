package com.example.HospitalAppointmentManager.presentation;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("")
@PageTitle("Login | HospitalAppointmentManager")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("HospitalAppointmentManager");
        add(title);

        // Information box
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.getStyle().set("background-color", "#f5f5f5"); // Light grey background
        infoLayout.setWidth("400px");
        infoLayout.setPadding(true);
        infoLayout.setMargin(true);

        // Course details
        Paragraph courseDetails = new Paragraph(
                "This is the software project for the course 'Entwurf von Softwaresystemen'. " +
                        "It serves as a demonstration of a full-stack Spring Boot service for a " +
                        " HospitalAppointmentManager designed with Vaadin."
        );
        // Assignment information
        Paragraph assignmentInfo = new Paragraph("ASSIGNMENT 6: Sejma Sijaric & Lorena Raichle");
        infoLayout.add(courseDetails, assignmentInfo);
        add(infoLayout);

        Span demoMessage = new Span("For demonstration purposes, any username and password will work to log in.");
        demoMessage.getStyle().set("color", "red");
        demoMessage.getStyle().set("font-weight", "bold");

        // Add the demo message above the LoginForm
        LoginForm loginForm = new LoginForm();
        add(demoMessage, loginForm);

        setHorizontalComponentAlignment(Alignment.CENTER, infoLayout);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.addLoginListener(e -> {
            // allow login
            System.out.println("Attempting login with username: " + e.getUsername() + " and password: " + e.getPassword());

            boolean isAuthenticated = authenticate(e.getUsername(), e.getPassword());
            if (isAuthenticated) {
                // Navigate to the MainView upon successful login
                getUI().ifPresent(ui -> ui.navigate("main"));
            } else {
                loginForm.setError(true);
            }

        });

        add(loginForm);
    }

    private boolean authenticate(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        // Allow any user to log in
        return true;
    }
}


