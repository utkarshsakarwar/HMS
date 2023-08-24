package com.amdocs.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.amdocs.test.modal.Doctor;


// @Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
