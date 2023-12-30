package com.example.HospitalAppointmentManager.service;

import com.example.HospitalAppointmentManager.model.Doctor;
import com.example.HospitalAppointmentManager.repository.DoctorRepository;
import com.vaadin.flow.component.notification.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




@Transactional
@Service
public class DoctorService implements ObjectService {

    @Autowired
    private DoctorRepository doctorRepository;
    ArrayList<Doctor> doctorList = new ArrayList<>();


    @Override
    public ArrayList<Doctor> findAll() {
        ArrayList<Doctor> doctorRepositoryList = (ArrayList<Doctor>) doctorRepository.findAll();
        if (doctorRepositoryList.isEmpty())
            return doctorList;
        else
            return doctorRepositoryList;
    }
    @Override
    public Doctor findById(long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public void add(Object o) {
        Doctor d = (Doctor) o;
        doctorList.add(d);
        doctorRepository.save(d);
    }

    @Override
    public void update(Object o) {
        Doctor d = (Doctor) o;
        for (Doctor doctor : doctorList) {
            if (d.getId() == doctor.getId()) {
                doctor.setFirstName(d.getFirstName());
                doctor.setLastName(d.getLastName());
                doctor.setEmail(d.getEmail());
                doctor.setAreaOfExpertise(d.getAreaOfExpertise());
                doctor.setAppointment_ID(d.getAppointment_ID());
                doctorRepository.save(doctor);
                break;
            }
        }
    }

    @Override
    public void delete(Object o) {
        Doctor d = (Doctor) o;
        for (Doctor doctor : doctorList) {
            if (doctor.getId() == d.getId()) {
                doctorList.remove(d);
                doctorRepository.delete(d);
                break;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            for (Doctor doctor : doctorList) {
                if (doctor.getId() == id) {
                    doctorList.remove(doctor);
                    doctorRepository.delete(doctor);
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
        doctorList.clear();
        doctorRepository.deleteAll();
    }

    public List<Doctor> filterDoctors(String filterText) {
        return doctorList.stream()
                .filter(doctor -> doctor.getFirstName().toLowerCase().contains(filterText)
                        || doctor.getLastName().toLowerCase().contains(filterText)
                        || doctor.getEmail().toLowerCase().contains(filterText)
                        || doctor.getAreaOfExpertise().toString().toLowerCase().contains(filterText))
                .distinct()
                .collect(Collectors.toList());
    }
}
