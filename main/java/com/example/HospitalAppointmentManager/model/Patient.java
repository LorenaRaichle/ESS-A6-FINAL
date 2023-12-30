package com.example.HospitalAppointmentManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue
    @Getter
    private long id;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String treatment;

    @OneToMany (mappedBy = "patient", fetch = FetchType.EAGER)
    @Getter @Setter
    private List<Appointment> appointment_ID = new LinkedList<>();

    public Patient (String firstName, String lastName, String email, String treatment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.treatment = treatment;
    }
}
