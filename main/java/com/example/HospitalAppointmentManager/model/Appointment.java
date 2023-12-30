package com.example.HospitalAppointmentManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Appointment {

    @Id @GeneratedValue
    @Getter
    private long id;
    @Getter @Setter
    private Date date;
    @Getter @Setter
    private String room;
    @Getter @Setter
    private AppointmentStatus appointmentStatus;
    @Getter @Setter
    private long doctorID;
    @Getter @Setter
    private long patientID;
    @ManyToOne @JoinColumn (name = "doctor")
    @Getter @Setter
    private Doctor doctor;
    @ManyToOne @JoinColumn (name = "patient")
    @Getter @Setter
    private Patient patient;

    @Getter @Setter
    private String doctorName;
    @Getter @Setter
    private String patientName;

    public Appointment (Date date, AppointmentStatus appointmentStatus, String room) {
        this.appointmentStatus = appointmentStatus;
        this.date = date;
        this.room = room;
    }
    public enum AppointmentStatus {Scheduled, Completed, Cancelled}
}
