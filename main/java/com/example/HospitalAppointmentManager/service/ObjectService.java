package com.example.HospitalAppointmentManager.service;

import java.util.Collection;

public interface ObjectService {
    Collection findAll ();
    Object findById (long id);
    void add(Object o);
    void update(Object o);
    void delete(Object o);
    void deleteById(long id);
    void deleteAll();

}
