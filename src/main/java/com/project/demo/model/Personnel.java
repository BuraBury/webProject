package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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
//    @Length(min=2, max=25)
    @NotNull
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate hireDate;
    @DecimalMin(value = "1500.0")
    private Double salary;
    private boolean sickLeave;
    //dodac plec jako ENUM, salary liczone na podstawie sickLeave

}
