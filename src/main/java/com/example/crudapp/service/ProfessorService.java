package com.example.crudapp.service;

import com.example.crudapp.model.Professor;
import com.example.crudapp.model.Address;
import com.example.crudapp.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private com.example.crudapp.repository.AddressRepository addressRepository;

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public Professor createProfessor(Professor professor) {
        if (professor.getAddress() != null) {
            Address address = professor.getAddress();
            if (address.getId() == null) {
                address = addressRepository.save(address);
            }
            professor.setAddress(address);
        }
        return professorRepository.save(professor);
    }

    public Professor updateProfessor(Long id, Professor professor) {
        if (!professorRepository.existsById(id)) {
            return null;
        }
        if (professor.getAddress() != null) {
            Address address = professor.getAddress();
            if (address.getId() == null) {
                address = addressRepository.save(address);
            }
            professor.setAddress(address);
        } else {
            professor.setAddress(null);
        }
        professor.setId(id);
        return professorRepository.save(professor);
    }

    public boolean deleteProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            return false;
        }
        professorRepository.deleteById(id);
        return true;
    }
}
