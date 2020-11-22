package com.project.demo.service;

import com.project.demo.exceptions.WrongPageException;
import com.project.demo.model.Personnel;
import com.project.demo.repository.PersonnelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

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
    public Personnel getPersonnelById(Long id) throws WrongPageException {
        return personnelRepository.findById(id).orElse(null);
    }

    @Override
    public List<Personnel> getAllPersonnel(Integer page, Integer size) {
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
        return personnelRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean removePersonnelById(Long id) {
        personnelRepository.deleteById(id);
        return true;
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        log.info("Tworze nowy personel");
        return personnelRepository.save(personnel);
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personals) {
        return personnelRepository.saveAll(personals);
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) {
        if (personnelRepository.existsById(id)) {
            personnel.setId(id);
            return personnelRepository.save(personnel);
        }
        return null;
    }

    @Override
    public List<Personnel> getPersonnelsBySickLeave(boolean sickLeave) {
        return personnelRepository.findPersonnelsBySickLeaveEquals(sickLeave);
    }

    @Override
    public List<Personnel> getPersonnelByPosition(String position) {
        return personnelRepository.selectAllPersonnelWithPositionEqualTo(position);
    }

    @Override
    public void cureAllPersonnel() {
        personnelRepository.updateAllPersonnelToBeHealthy();

    }
}
