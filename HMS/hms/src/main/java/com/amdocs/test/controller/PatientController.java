package com.amdocs.test.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.test.exceptions.ResourceNotFoundException;
import com.amdocs.test.modal.Patient;
import com.amdocs.test.repositories.PatientRepository;

// import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;


@RestController
@RequestMapping("/api/v1")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public List < Patient > getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity < Patient > getPatientById(@PathVariable(value = "id") Long patientId)
    throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("patient not found for this id :: " + patientId));
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity < Patient > updatePatient(@PathVariable(value = "id") Long patientId,
        @RequestBody Patient patientDetails) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("patient not found for this id :: " + patientId));

        patient.setAddress(patientDetails.getAddress());
        patient.setAge(patient.getAge());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setSex(patientDetails.getSex());
        patient.setPatientName(patientDetails.getPatientName());
        final Patient updatedPatient = patientRepository.save(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/patients/{id}")
    public Map < String, Boolean > deletePatient(@PathVariable(value = "id") Long patientId)
    throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("patient not found for this id :: " + patientId));

        patientRepository.delete(patient);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

