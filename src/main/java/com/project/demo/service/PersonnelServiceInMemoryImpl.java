package com.project.demo.service;

import com.project.demo.model.Personnel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class PersonnelServiceInMemoryImpl implements PersonnelService {

    private final Map<Long, Personnel> personnelMap = new HashMap<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        personnelMap.put(nextId, Personnel.builder()
                .id(nextId)
                .firstName("Właściciel")
                .lastName("Hotelu")
                .hireDate(LocalDate.parse("1800-01-01"))
                .position("Boss")
                .salary(12000.0)
                .sickLeave(false)
                .build());
        nextId++;
    }

    @Override
    public Personnel getPersonnelById(Long id) {
        return personnelMap.getOrDefault(id, null);
    }

    @Override
    public List<Personnel> getAllPersonnel() {
        return new ArrayList<>(personnelMap.values());
    }

    @Override
    public boolean removePersonnelById(Long id) {
        if (personnelMap.containsKey(id)) {
            personnelMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        personnel.setId(getNextId());
        return personnelMap.put(personnel.getId(), personnel);
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personals) {
        return addPersonnel(personals);
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) {
        if (personnelMap.containsKey(id)) {
            if (personnel.getFirstName() != null) {
                personnelMap.get(id).setFirstName(personnel.getFirstName());
            }
            if (personnel.getLastName() != null) {
                personnelMap.get(id).setLastName(personnel.getLastName());
            }
            if (personnel.getSalary() != null) {
                personnelMap.get(id).setSalary(personnel.getSalary());
            }
            if (personnel.isSickLeave() != personnelMap.get(id).isSickLeave()) {
                personnelMap.get(id).setSickLeave(personnel.isSickLeave());
            }
            if (personnel.getPosition() != null) {
                personnelMap.get(id).setPosition(personnel.getPosition());
            }
            if (personnel.getHireDate() != null) {
                personnelMap.get(id).setHireDate(personnel.getHireDate());
            }
            return personnelMap.get(id);
        }
        return null;

    }

    private List<Personnel> addPersonnel(List<Personnel> personnels) {
        personnels.forEach(personnel -> {
            personnel.setId(getNextId());
            personnelMap.put(personnel.getId(), personnel);
        });

        return personnels;
    }

    private Long getNextId() {
        return nextId++;
    }
}

