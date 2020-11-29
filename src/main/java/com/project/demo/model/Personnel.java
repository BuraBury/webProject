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
import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "personnel")
public class Personnel {
    @Id
    @GeneratedValue
    private Long id;
    @Length(min=2, max=25)
    private String firstName;
    @Length(min=2, max=25)
    private String lastName;
    @Length(min=3, max=45)
    private String position;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    @DecimalMin(value = "1500.0")
    private Double salary;
    private boolean sickLeave;
    //dodac plec jako ENUM, salary liczone na podstawie sickLeave

}
