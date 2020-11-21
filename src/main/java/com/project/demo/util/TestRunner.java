package com.project.demo.util;

import com.project.demo.model.Personnel;
import com.project.demo.repository.OldPersonnelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class TestRunner implements CommandLineRunner {

    private final OldPersonnelRepository personnelRepository;

    public TestRunner(OldPersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
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

        log.info("Nowy pracownik dodany do bazy: " + personnelRepository.create(personnel).toString());
        log.info("Nowy pracownik dodany do bazy: " + personnelRepository.create(personnel1).toString());

    }
}
