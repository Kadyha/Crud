package com.example.crudapp.service;

import com.example.crudapp.model.Person;
import com.example.crudapp.model.Address;
import com.example.crudapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private com.example.crudapp.repository.AddressRepository addressRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("El objeto 'person' no puede ser nulo. Verifica el envelope SOAP enviado.");
        }
        if (person.getAddress() != null) {
            Address address = person.getAddress();
            if (address.getId() == null) {
                address = addressRepository.save(address);
            }
            person.setAddress(address);
        }
        // Validación SOLO para Student
        if (person instanceof com.example.crudapp.model.Student) {
            com.example.crudapp.model.Student student = (com.example.crudapp.model.Student) person;
            if (student.getStudentNumber() == null || student.getStudentNumber().trim().isEmpty()) {
                throw new IllegalArgumentException("El campo studentNumber no puede ser nulo ni vacío para Student.");
            }
        }
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person personDetails) {
        return personRepository.findById(id).map(person -> {
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            person.setEmail(personDetails.getEmail());
            person.setPhone(personDetails.getPhone());
            if (personDetails.getAddress() != null) {
                Address address = personDetails.getAddress();
                if (address.getId() == null) {
                    address = addressRepository.save(address);
                }
                person.setAddress(address);
            } else {
                person.setAddress(null);
            }
                // Validación SOLO para Student
                if (person instanceof com.example.crudapp.model.Student) {
                    com.example.crudapp.model.Student student = (com.example.crudapp.model.Student) person;
                    if (student.getStudentNumber() == null || student.getStudentNumber().trim().isEmpty()) {
                        throw new IllegalArgumentException("El campo studentNumber no puede ser nulo ni vacío para Student.");
                    }
                }
            return personRepository.save(person);
        }).orElse(null);
    }

    public boolean deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
