package com.example.HospitalAppointmentManager.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@NoArgsConstructor
public class Doctor {

    public enum AreaOfExpertise {
        Pediatrics, Neurosurgery, Cardiology, Pulmonology, Gastroenterology,
        Nephrology, Endocrinology, Radiology, Pathology, Rheumatology, Hematology,
        Neurology, Anesthesiology, Ophthalmology, Otolaryngology, Urology, Dermatology
    }

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
    private AreaOfExpertise areaOfExpertise;
    @OneToMany (mappedBy = "doctor", fetch = FetchType.EAGER)
    @Getter @Setter
    private List<Appointment> appointment_ID;
    public Doctor (String firstName, String lastName, String email, AreaOfExpertise areaOfExpertise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.areaOfExpertise = areaOfExpertise;
    }
}
