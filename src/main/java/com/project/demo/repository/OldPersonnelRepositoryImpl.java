package com.project.demo.repository;

import com.project.demo.model.Personnel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//Repository powinnismy oznaczac klasy obslugujace dostep do bazy danych
//Przy wykorzystaniu baz danych powinnismy oznaczyc kale badz metody jako transactional
//Chyba ze chcemy zarzadzac tranzakcjami wlasnorecznie
@Repository
@Transactional
@Deprecated
public class OldPersonnelRepositoryImpl implements OldPersonnelRepository {

    private final EntityManager entityManager;

    public OldPersonnelRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Personnel create(Personnel personnel) {
        entityManager.persist(personnel);
        return personnel;
    }

    @Override
    public Optional<Personnel> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Personnel.class, id));
    }

    @Override
    public List<Personnel> findAll() {
        return entityManager.createQuery("SELECT p FROM personnel p", Personnel.class).getResultList();
    }

    @Override
    public Personnel update(Personnel personnel) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
