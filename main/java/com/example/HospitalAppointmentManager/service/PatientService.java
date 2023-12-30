package com.example.HospitalAppointmentManager.service;

import com.example.HospitalAppointmentManager.model.Patient;
import com.example.HospitalAppointmentManager.repository.PatientRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PatientService implements ObjectService {

    @Autowired
    private PatientRepository patientRepository;
    ArrayList<Patient> patientList = new ArrayList<>();

   // @Autowired
    //private AppointmentService appointmentService;

    @Override
    public ArrayList<Patient> findAll() {
        ArrayList<Patient> patientRepositoryList = (ArrayList<Patient>) patientRepository.findAll();
        if (patientRepositoryList.isEmpty())
            return patientList;
        else
            return patientRepositoryList;
    }
    @Override
    public Patient findById(long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void add(Object o) {
        Patient p = (Patient) o;
        patientList.add(p);
        patientRepository.save(p);
    }

    @Override
    public void update(Object o) {
        Patient p = (Patient) o;
        for (Patient patient : patientList) {
            if (p.getId() == patient.getId()) {
                patient.setFirstName(p.getFirstName());
                patient.setLastName(p.getLastName());
                patient.setEmail(p.getEmail());
                patient.setTreatment(p.getTreatment());
                patient.setAppointment_ID(p.getAppointment_ID());
                patientRepository.save(patient);
                break;
            }
        }
    }

    @Override
    public void delete(Object o) {
        Patient d = (Patient) o;
        for (Patient patient : patientList) {
            if (patient.getId() == d.getId()) {
                patientList.remove(d);
                patientRepository.delete(d);
                break;
            }
        }
    }




    @Override
    public void deleteById(long id) {
        try {
            for (Patient patient : patientList) {
                if (patient.getId() == id) {
                    patientList.remove(patient);
                    patientRepository.delete(patient);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Notification.show("An error occurred.", 3000, Notification.Position.MIDDLE);
        }

    }

    @Override
    public void deleteAll() {
        patientList.clear();
        patientRepository.deleteAll();
    }

    public List<Patient> filterPatients(String filterText) {
        return patientList.stream()
                .filter(patient -> patient.getFirstName().toLowerCase().contains(filterText)
                        || patient.getLastName().toLowerCase().contains(filterText)
                        || patient.getEmail().toLowerCase().contains(filterText)
                        || patient.getTreatment().toString().toLowerCase().contains(filterText))
                .distinct()
                .collect(Collectors.toList());
    }
}
