package com.project.demo.service;

import com.project.demo.model.Personnel;

import java.util.List;

public interface PersonnelService {

    Personnel getPersonnelById(Long id);

    List<Personnel> getAllPersonnel(Integer page, Integer size);

    boolean removePersonnelById(Long id);

    Personnel createNewPersonnel(Personnel personnel);

    List<Personnel> createBatchOfPersonnel(List<Personnel> personals);

    Personnel updatePersonnelById(Long id, Personnel personnel);

    List<Personnel> getPersonnelsBySickLeave(boolean sickLeave);

    List <Personnel> getPersonnelByPosition(String position);

    void cureAllPersonnel();
}
