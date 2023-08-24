package com.amdocs.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.amdocs.test.modal.Patient;


// @Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

}
