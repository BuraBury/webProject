package com.project.demo.service;

import com.project.demo.model.Personnel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonnelService {

    Optional<Personnel> getPersonnelById(Long id);

    List<Personnel> getAllPersonnel(Integer page, Integer size);

    boolean removePersonnelById(Long id);

    Personnel createNewPersonnel(Personnel personnel);

    List<Personnel> createBatchOfPersonnel(List<Personnel> personals);

    Personnel updatePersonnelById(Long id, Personnel personnel);

    List<Personnel> getPersonnelBySickLeave(boolean sickLeave);

    List <Personnel> getPersonnelByPosition(String position);

    void cureAllPersonnel();

    List<Personnel> getSomeSpecialPersonnel(Long id, String firstName, String lastName,
                                            String position, Double salary, String hireDate, Boolean sickLeave);

}
