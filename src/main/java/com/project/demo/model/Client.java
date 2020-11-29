package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "client")
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    @Length(min=2, max=25)
    private String firstName;
    @Length(min=2, max=25)
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    @Length(min=2, max=25)
    private String passportNumber;
    @Length(min=1, max=3)
    private String roomNumber;

}


