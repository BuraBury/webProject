package com.project.demo.util;

import com.project.demo.model.Client;
import com.project.demo.model.Personnel;
import com.project.demo.repository.ClientRepository;
import com.project.demo.repository.PersonnelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class TestRunner implements CommandLineRunner {

    private final PersonnelRepository personnelRepository;
    private final ClientRepository clientRepository;


    public TestRunner(PersonnelRepository personnelRepository, ClientRepository clientRepository) {
        this.personnelRepository = personnelRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Personnel personnel = Personnel.builder()
                .firstName("Paulina")
                .lastName("Bury")
                .hireDate(LocalDate.parse("2020-03-01"))
                .position("CEO")
                .salary(1000.00)
                .sickLeave(false)
                .build();
        Personnel personnel1 = Personnel.builder()
                .firstName("Jakub")
                .lastName("Nagiet")
                .hireDate(LocalDate.parse("2019-02-01"))
                .position("Manager")
                .salary(130000.00)
                .sickLeave(false)
                .build();

        Client client = Client.builder()
                .firstName("Jan")
                .lastName("Brzechwa")
                .arrivalDate(LocalDate.parse("2020-11-21"))
                .departureDate(LocalDate.parse("2020-11-30"))
                .passportNumber("123-456-21")
                .roomNumber("5601")
                .build();

        log.info("Nowy pracownik dodany do bazy: " + personnelRepository.save(personnel).toString());
        log.info("Nowy pracownik dodany do bazy: " + personnelRepository.save(personnel1).toString());
        log.info("Nowy klient dodany do bazy: " + clientRepository.save(client).toString());

    }

}
