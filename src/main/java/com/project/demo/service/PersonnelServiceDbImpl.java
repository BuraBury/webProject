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
        if (personnel.getFirstName().length() < 2 || personnel.getLastName().length() < 2) {
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
        log.info("Zmieniono wartość parametru sickLeave wszystkim pracownikom na 'false'");
        personnelRepository.updateAllPersonnelToBeHealthy();
    }

    @Override
    public List<Personnel> getSomeSpecialPersonnel(Long id, String firstName,
                                                   String lastName, String position,
                                                   Double salary, String hireDate,
                                                   Boolean sickLeave) throws WrongDataException {
        if (firstName == null && lastName == null && position == null && salary == null && hireDate == null) {
            List<Personnel> list = personnelRepository.findPersonnelByIdEquals(id);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracownika o id: %d", id));
            return personnelRepository.findPersonnelByIdEquals(id);
        }
        if (id == null && lastName == null && position == null && salary == null && hireDate == null) {
            List<Personnel> list = personnelRepository.findPersonnelByFirstNameEquals(firstName);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników o imieniu: %s", firstName));
            return list;
        }
        if (firstName == null && id == null && position == null && salary == null && hireDate == null) {
            List<Personnel> list = personnelRepository.findPersonnelByLastNameEquals(lastName);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników o nazwisku: %s", lastName));
            return list;
        }
        if (firstName == null && lastName == null && id == null && salary == null && hireDate == null) {
            List<Personnel> list = personnelRepository.findPersonnelByPositionEquals(position);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników na stanowisku: %s", position));
            return list;
        }
        if (firstName == null && lastName == null && position == null && id == null && hireDate == null) {
            List<Personnel> list = personnelRepository.findPersonnelBySalaryEquals(salary);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników zarabiających: %szł", salary));
            return list;
        }
        if (firstName == null && lastName == null && position == null && salary == null && id == null && hireDate != null) {
            List<Personnel> list = personnelRepository.findPersonnelByHireDateEquals(LocalDate.parse(hireDate));
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników zatrudnionego: %s", hireDate.toString()));
            return list;
        }
        if (position == null && salary == null && id == null) {
            List<Personnel> list = personnelRepository.findPersonnelByFirstNameAndLastName(firstName, lastName);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników o imieniu: %s i naziwsku: %s", firstName, lastName));
            return list;
        }
        if (hireDate == null && lastName == null && id == null && position == null) {
            List<Personnel> list = personnelRepository.findPersonnelBySalaryAndFirstName(salary, firstName);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników zarabiających: %s o imieniu: %s", salary, firstName));
            return list;
        }
        if (firstName == null && lastName == null && salary == null && id == null) {
            List<Personnel> list = personnelRepository.findPersonnelByPositionAndSickLeave(position, sickLeave);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników na stanowisku: %s o statusie zwolnienia chorobowego: %s", position, sickLeave));
            return list;
        }
        if (id == null && firstName == null && lastName == null) {
            List<Personnel> list = personnelRepository.findPersonnelByPositionAndSalaryEqual(position, salary);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników na stanowisku: %s zarabiających: %s", position, salary));
            return list;
        }
        if (firstName != null && lastName != null && position != null) {
            List<Personnel> list = personnelRepository.getPersonnelByFirstNameLastNameAndPosition(firstName, lastName, position);
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Nie znaleziono pracowników o imieniu: %s, nazwisku: %s, na stanowisku: %s", firstName, lastName, position));
            return list;
        }

        if (position != null && hireDate != null && sickLeave == null) {
            List<Personnel> list = personnelRepository.getPersonnelByPositionAndHireDateEqual(position, hireDate.toString());
            if (isListEmpty(list))
                throw new WrongDataException(String.format("Brak pracowników na stanowisku: %s zatrudnionych: %s", position, hireDate));
            return list;
        }
        log.info("Wprowadzono błędne dane");
        throw new WrongDataException("Wprowadzono błędne dane");
    }

    private boolean isListEmpty(List<?> list) {
        return list.size() == 0;
    }
}

