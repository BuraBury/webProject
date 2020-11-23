package com.project.demo.service;

import com.project.demo.exceptions.WrongDataException;
import com.project.demo.exceptions.WrongIdException;
import com.project.demo.model.Personnel;

import com.project.demo.exceptions.WrongPageException;
import com.project.demo.repository.PersonnelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Scope("prototype")
@Slf4j
@Primary
public class PersonnelServiceDbImpl implements PersonnelService {

    private final PersonnelRepository personnelRepository;

    public PersonnelServiceDbImpl(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public Optional<Personnel> getPersonnelById(Long id) throws WrongIdException {
        if (personnelRepository.findById(id).isEmpty()) {
            log.info("Podane id pracownika nie istnieje w bazie; id = " + id);
            throw new WrongIdException("Błędne id pracownika; id = " + id);
        } else {
            log.info("Odnaleziono pracownika po id; id = " + id);
            return personnelRepository.findById(id);
        }

    }

    @Override
    public List<Personnel> getAllPersonnel(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 1) {

            throw new WrongPageException("Strona nie moze byc mniejsza niz 1");
        }

        Sort sort = Sort.by("salary").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        log.info("Wyszukano pracowników");
        return personnelRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean removePersonnelById(Long id) throws WrongIdException {
        if (personnelRepository.findById(id).isEmpty()) {
            log.info("Podane id pracownika nie istnieje; id = " + id);
            throw new WrongIdException("Podane id pracownika nie istnieje; id = " + id);
        } else {
            log.info("Poprawnie usunięto z bazy pracownika o id = " + id);
            personnelRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) throws WrongDataException {
        if (personnel.getFirstName().length() < 2 || personnel.getLastName().length()< 2) {
            log.info("Nie udalo sie dodac pracownika");
            throw new WrongDataException("Błędne dane");
        } else {
            log.info("Dodano nowego pracownika");
            return personnelRepository.save(personnel);
        }


    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personals) {
        log.info("Dodano liste pracowników");
        return personnelRepository.saveAll(personals);
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) throws WrongIdException {
        if (personnelRepository.existsById(id)) {
            personnel.setId(id);
            log.info("Dane pracownika zostały zaktualizowane pomyślnie");
            return personnelRepository.save(personnel);
        } else {
            log.info("Podane id pracownika nie istnieje; id = " + id);
            throw new WrongIdException("Podane id pracownika nie istnieje; id = " + id);
        }
    }

    @Override
    public List<Personnel> getPersonnelBySickLeave(boolean sickLeave) {
        return personnelRepository.findPersonnelsBySickLeaveEquals(sickLeave);
    }

    @Override
    public List<Personnel> getPersonnelByPosition(String position) {
        return personnelRepository.findPersonnelByPositionEquals(position);
    }

    @Override
    public void cureAllPersonnel() {
        personnelRepository.updateAllPersonnelToBeHealthy();

    }

    @Override
    public List<Personnel> getSomeSpecialPersonnel(Long id, String firstName, String lastName,
                                                   String position, Double salary, LocalDate hireDate, Boolean sickLeave) {
        if (firstName == null && lastName == null && position == null && salary == null && hireDate == null) {
            return personnelRepository.findPersonnelByIdEquals(id);
        }
        if (id == null && lastName == null && position == null && salary == null && hireDate == null) {
            return personnelRepository.findPersonnelByFirstNameEquals(firstName);
        }
        if (firstName == null && id == null && position == null && salary == null && hireDate == null) {
            return personnelRepository.findPersonnelsByLastNameEquals(lastName);
        }
        if (firstName == null && lastName == null && id == null && salary == null && hireDate == null) {
            return personnelRepository.findPersonnelByPositionEquals(position);
        }
        if (firstName == null && lastName == null && position == null && id == null && hireDate == null) {
            return personnelRepository.findPersonnelsBySalaryEquals(salary);
        }
        if (firstName == null && lastName == null && position == null && salary == null && id == null) {
            return personnelRepository.findPersonnelsByHireDateEquals(hireDate);
        }
        if (position == null && salary == null && id == null) {
            return personnelRepository.findPersonnelsByFirstNameAndLastName(firstName, lastName);
        }
        if (hireDate == null && lastName == null && id == null && position == null) {
            return personnelRepository.findPersonnelsBySalaryAndFirstName(salary, firstName);
        }
        if (firstName == null && lastName == null && salary == null && id == null) {
            return personnelRepository.findPersonnelsByPositionAndSickLeave(position, sickLeave);
        }
        log.info("Brak takiego pracownika w bazie");
        return null;
    }
}

