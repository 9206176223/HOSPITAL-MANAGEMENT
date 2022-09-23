package com.example.Doctor.contoller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Doctor.exception.ResourceNotFoundException;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.repository.DoctorRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class DoctorController {
	@Autowired
	private DoctorRepository doctorRepository;
	
	
	@GetMapping("/doctors")
	public List<Doctor> getAllDoctors(){
		return doctorRepository.findAll();
	}		
	
	
	@PostMapping("/doctors")
	public Doctor createDoctor(@RequestBody Doctor Doctor) {
		return doctorRepository.save(Doctor);
	}
	
	
	@GetMapping("/Doctors/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) throws ResourceNotFoundException {
		Doctor Doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id :" + id));
		return ResponseEntity.ok(Doctor);
	}
	
	
	
	@PutMapping("/Doctor/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor DoctorDetails){
		Doctor Doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id :" + id));
		
		Doctor.setId(DoctorDetails.getId());
		Doctor.setName(DoctorDetails.getName());
		Doctor.setAge(DoctorDetails.getAge());
		Doctor.setGender(DoctorDetails.getGender());
		Doctor.setSpecialist(DoctorDetails.getSpecialist());
		
		Doctor updatedDoctor = doctorRepository.save(Doctor);
		return ResponseEntity.ok(updatedDoctor);
	}
	
	@DeleteMapping("/Doctor/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id){
		Doctor Doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id :" + id));
		
		doctorRepository.delete(Doctor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	

}


