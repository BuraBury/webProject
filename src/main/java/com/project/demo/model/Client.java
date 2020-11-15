package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private String passportNumber;
    //TODO: numer pokoju ??
}

//TODO: uzupelnic class Customer
//TODO: dodac nowy service i controller obslugujacy zarzadzanie clientami
//TODO: dodac clientow do pamieci
