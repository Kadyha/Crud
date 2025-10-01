package com.example.crudapp;

import com.example.crudapp.model.Person;
import com.example.crudapp.model.Address;
import com.example.crudapp.repository.PersonRepository;
import com.example.crudapp.repository.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrudAppApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, AddressRepository addressRepository) {
        return args -> {
            Address address1 = new Address();
            address1.setStreet("123 Main St");
            address1.setCity("Springfield");
            address1.setState("IL");
            address1.setPostalCode("62704");
            address1.setCountry("USA");
            addressRepository.save(address1);

            Address address2 = new Address();
            address2.setStreet("456 Elm St");
            address2.setCity("Shelbyville");
            address2.setState("IL");
            address2.setPostalCode("62565");
            address2.setCountry("USA");
            addressRepository.save(address2);

            Person john = new Person();
            john.setFirstName("John");
            john.setLastName("Doe");
            john.setEmail("john.doe@example.com");
            john.setPhone("1234567890");
            john.setAddress(address1);
            personRepository.save(john);

            Person jane = new Person();
            jane.setFirstName("Jane");
            jane.setLastName("Smith");
            jane.setEmail("jane.smith@example.com");
            jane.setPhone("0987654321");
            jane.setAddress(address2);
            personRepository.save(jane);
        };
    }
}
