package com.project.demo.repository;

import com.project.demo.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    List<Personnel> findPersonnelsBySickLeaveEquals(Boolean sickLeave); //zaawansowane zapytania do bazy

    //nativeQuery -> false = HQL; nativeQuery -> true SQL
    @Query(value = "SELECT p FROM personnel p WHERE p.position= :position", nativeQuery = false)
    List<Personnel> findPersonnelByPositionEquals(@Param("position") String position);
    /*
    to samo co
    List <Personnel> findPersonnelByPositionEqualTo(String position);
    */
    @Query(value = "SELECT p from personnel p WHERE p.id= :id", nativeQuery = false)
    List<Personnel> findPersonnelByIdEquals(@Param("id") Long id);

    @Query(value = "SELECT p from personnel p WHERE p.firstName= :firstName", nativeQuery = false)
    List<Personnel> findPersonnelByFirstNameEquals(@Param("firstName") String firstName);

    @Query(value = "SELECT p from personnel p where p.lastName= :lastName", nativeQuery = false)
    List<Personnel> findPersonnelByLastNameEquals(@Param("lastName") String lastName);

    @Query(value = "SELECT p from personnel p where p.salary= :salary", nativeQuery = false)
    List<Personnel> findPersonnelBySalaryEquals(@Param("salary") Double salary);

    @Query(value = "SELECT p from personnel p where p.hireDate= :hireDate", nativeQuery = false)
    List<Personnel> findPersonnelByHireDateEquals(@Param("hireDate") LocalDate hireDate);

    @Query(value = "SELECT p from personnel p where p.firstName= :firstName AND p.lastName= :lastName", nativeQuery = false)
    List<Personnel> findPersonnelByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT p from personnel p where p.salary= :salary AND p.firstName= :firstName", nativeQuery = false)
    List<Personnel> findPersonnelBySalaryAndFirstName(@Param("salary") Double salary, @Param("firstName") String firstName);

    @Query(value = "SELECT p from personnel p where p.position= :position and p.sickLeave = :sickLeave", nativeQuery = false)
    List<Personnel> findPersonnelByPositionAndSickLeave(@Param("position") String position, @Param("sickLeave") Boolean sickLeave);

    @Query(value = "select p from personnel p where p.position= :position and p.salary= :salary", nativeQuery = false)
    List<Personnel> findPersonnelByPositionAndSalaryEqual(@Param("position") String position, @Param("salary") Double salary);

    @Query(value = "select p from personnel p where p.firstName= :firstName and p.lastName= :lastName and p.position= :position", nativeQuery = false)
    List<Personnel> getPersonnelByFirstNameLastNameAndPosition(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("position") String position);

    @Modifying
    @Query(value = "update personnel p set p.sickLeave = false")
    @Transactional
    void updateAllPersonnelToBeHealthy();



}

