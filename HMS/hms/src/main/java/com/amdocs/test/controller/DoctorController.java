package com.amdocs.test.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.test.exceptions.ResourceNotFoundException;
import com.amdocs.test.modal.Doctor;
import com.amdocs.test.repositories.DoctorRepository;

@RestController("/api/v1")
public class DoctorController {
    @Autowired DoctorRepository doctorRepository;
    @GetMapping("/doctors")
    public List< Doctor > getAllDoctors(){
        return doctorRepository.findAll();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity < Doctor > getDoctorById(@PathVariable(value = "id") Long doctorId)
    throws ResourceNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for this id :: " + doctorId));
        return ResponseEntity.ok().body(doctor);
    }

    @PostMapping("/doctors")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity < Doctor > updateDoctor(@PathVariable(value = "id") Long doctorId,
        @RequestBody Doctor doctorDetails) throws ResourceNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("doctor not found for this id :: " + doctorId));

        doctor.setDateOfBirth(doctorDetails.getDateOfBirth());
        doctor.setDoctorName(doctorDetails.getDoctorName());
        doctor.setPatientId(doctorDetails.getPatientId());
        doctor.setPatientName(doctorDetails.getPatientName());
        doctor.setQualification(doctorDetails.getQualification());
        final Doctor updatedDoctor = doctorRepository.save(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/doctors/{id}")
    public Map < String, Boolean > deleteDoctor(@PathVariable(value = "id") Long doctorId)
    throws ResourceNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("doctor not found for this id :: " + doctorId));

        doctorRepository.delete(doctor);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
