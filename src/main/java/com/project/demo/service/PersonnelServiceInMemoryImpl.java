package com.project.demo.service;

import com.project.demo.config.HotelPersonnelConfig;
import com.project.demo.model.Personnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Scope("singleton")
@Slf4j
@Profile("old")
@Deprecated
public class PersonnelServiceInMemoryImpl implements PersonnelService {

    private final Map<Long, Personnel> personnelMap = new HashMap<>();
    private Long nextId = 1L;
    private final HotelPersonnelConfig hotelPersonnelConfig;

    //wstrzykiwanie wartosci z pliku application--a.properties:
    @Value("${hotel.personel.owner.name}")
    private String ownerName;
    @Value("${hotel.personel.owner.salary:20000.0}")
    private Double salary;
    @Value("${hotel.personel.owner.last-name}")
    private String ownerLastName;
    @Value("${hotel.personel.owner.position}")
    private String ownerPosition;
    @Value("${hotel.personel.owner.sick-leave}")
    private Boolean ownerSickLeave;

    public PersonnelServiceInMemoryImpl(HotelPersonnelConfig hotelPersonnelConfig) {
        this.hotelPersonnelConfig = hotelPersonnelConfig;
    }


//    @PostConstruct
//    public void init() {
//        personnelMap.put(nextId, Personnel.builder()
//                .id(nextId)
//                .firstName(ownerName)
//                .lastName(ownerLastName)
//                .hireDate(LocalDate.parse("2020-03-01"))
//                .position(ownerPosition)
//                .salary(salary)
//                .sickLeave(ownerSickLeave)
//                .build());
//
//        personnelMap.put(nextId, Personnel.builder()
//                .id(nextId)
//                .firstName(hotelPersonnelConfig.getNames().get(2))
//                .lastName(ownerLastName)
//                .hireDate(LocalDate.parse("2020-03-01"))
//                .position(ownerPosition)
//                .salary(salary)
//                .sickLeave(ownerSickLeave)
//                .build());
//
//        personnelMap.put(nextId, Personnel.builder()
//                .id(nextId)
//                .firstName(hotelPersonnelConfig.getNames().get(1))
//                .lastName(hotelPersonnelConfig.getPeople().get(hotelPersonnelConfig.getNames().get(1)))
//                .hireDate(LocalDate.parse("2020-03-01"))
//                .position(ownerPosition)
//                .salary(salary)
//                .sickLeave(ownerSickLeave)
//                .build());
//
//        nextId++;
//        log.info("blablabla");
//
//    }

    @Override
    public Optional<Personnel> getPersonnelById(Long id) {
        return Optional.ofNullable(personnelMap.getOrDefault(id, null));
    }

    @Override
    public List<Personnel> getAllPersonnel(Integer page, Integer size) {
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


    @Override
    public List<Personnel> getPersonnelBySickLeave(boolean sickLeave) {
        return null;
    }

    @Override
    public List<Personnel> getPersonnelByPosition(String position) {
        return null;
    }

    @Override
    public void cureAllPersonnel() {

    }

    @Override
    public List<Personnel> getSomeSpecialPersonnel(Long id, String firstName, String lastName, String position, Double salary, String hireDate, Boolean isSick) {
        return null;
    }

//    @Override
//    public List<Personnel> findByKeyword(String keyword) {
//        return null;
//    }

    public List<Personnel> findSickPersonnelsByPositions(String position) {
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

