package com.project.demo.repository;

import com.project.demo.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    List<Personnel> findPersonnelsBySickLeaveEquals(Boolean sickLeave); //zaawansowane zapytania do bazy

    //nativeQuery -> false = HQL; nativeQuery -> true SQL
    @Query(value = "SELECT p FROM personnel p WHERE p.position= :position", nativeQuery = false)
    List<Personnel> selectAllPersonnelWithPositionEqualTo(@Param("position") String position);
    /*
    to samo co
    List <Personnel> findPersonnelByPositionEqualTo(String position);
    */

    @Modifying
    @Query(value = "update personnel p set p.sickLeave = false")
    @Transactional
    void updateAllPersonnelToBeHealthy();
}
