package com.example.crudapp.service;

import com.example.crudapp.model.Student;
import com.example.crudapp.model.Address;
import com.example.crudapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private com.example.crudapp.repository.AddressRepository addressRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        if (student.getAddress() != null) {
            Address address = student.getAddress();
            if (address.getId() == null) {
                address = addressRepository.save(address);
            }
            student.setAddress(address);
        }
        // Validación para evitar duplicidad de studentNumber
        if (student.getStudentNumber() != null && studentRepository.findAll().stream()
                .anyMatch(s -> s.getStudentNumber() != null && s.getStudentNumber().equals(student.getStudentNumber()))) {
            throw new IllegalArgumentException("El studentNumber ya existe. Debe ser único.");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        if (!studentRepository.existsById(id)) {
            return null;
        }
        if (student.getAddress() != null) {
            Address address = student.getAddress();
            if (address.getId() == null) {
                address = addressRepository.save(address);
            }
            student.setAddress(address);
        } else {
            student.setAddress(null);
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
