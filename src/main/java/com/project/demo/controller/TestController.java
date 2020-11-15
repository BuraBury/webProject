package com.project.demo.controller;

import com.project.demo.model.Client;
import com.project.demo.model.Personnel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/")
public class TestController {

//    @GetMapping("/helloPersonnel")
//    public Personnel sayHello() {
//        return new Personnel(100L, "Jacek", "Warzycha",
//                "Parkingowy", LocalDate.parse("2000-11-15"), 3500.0, false);
//    }
//
//    @GetMapping("/helloClient")
//    public Client sayHelloToClient() {
//        return new Client("Paulina", 200L);
//    }
}
