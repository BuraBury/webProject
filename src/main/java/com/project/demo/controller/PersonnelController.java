package com.project.demo.controller;

import com.project.demo.model.Personnel;
import com.project.demo.service.PersonnelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/hotel/personnel")
@Slf4j
public class PersonnelController {

    private final PersonnelService personnelService;

    //tu wstrzykujemy potrzebne serwisy
    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    //metoda adnotowana jako GetMapping zostanie wywoalana na zadanie: localhost:<PORT>/hotel/personel/<id>
    // gdzie id to numer pracownika
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonnelByID(@PathVariable Long id) {
        Personnel personnel = personnelService.getPersonnelById(id);
        //jezeli znalazlo pracownika zwraca go
        if (Objects.nonNull(personnel)) {
            return ResponseEntity.ok(personnel);
        }
        //jesli nie 404 not found
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Personnel>> getAllPersonnel(@RequestParam(required = false) Integer page,
                                                           @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getAllPersonnel(page, size));
    }

    //DeleteMapping powinien sluzyc do usuwania zasobow serwisu.
    // W tym przypadku jesli sie uda to 204, jesli nie to 400
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonnel(@PathVariable Long id) {
        if (personnelService.removePersonnelById(id)) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //PostMapping powinien sluzyc do tworzenia nowych zasobow
    @PostMapping("/batch")
    public ResponseEntity<?> createPersonnel(@RequestBody List<Personnel> personnel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personnelService.createBatchOfPersonnel(personnel));
    }

    @PostMapping
    public ResponseEntity<?> createPersonnel(@Valid @RequestBody Personnel personnel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personnelService.createNewPersonnel(personnel));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePersonnelById(@PathVariable Long id, @RequestBody Personnel personnel) {
        if (Objects.nonNull(personnelService.getPersonnelById(id))) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(personnelService.updatePersonnelById(id, personnel));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/sick/{sickLeave}")
    public ResponseEntity<?> getPersonnlesBySickLeave(@PathVariable Boolean sickLeave) {
        return ResponseEntity.ok(personnelService.getPersonnelsBySickLeave(sickLeave));
    }

    @GetMapping("/position")
    public ResponseEntity<?> getPersonnelByPosition(@RequestParam("position") String position) {
        return ResponseEntity.ok(personnelService.getPersonnelByPosition(position));
    }

    @GetMapping("/cure")
    public void cureAllPersonnel() {
        personnelService.cureAllPersonnel();
    }

}
