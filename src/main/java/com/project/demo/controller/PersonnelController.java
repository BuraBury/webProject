package com.project.demo.controller;

import com.project.demo.model.Personnel;
import com.project.demo.service.PersonnelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    public String welcomePage(ModelMap modelMap) {
        modelMap.addAttribute("hotelName", "Paradiso Hotel");
        return "welcome";
    }

//    @GetMapping("/personnel")
//    public String personnel(ModelMap modelMap, String keyword) {
//        if (keyword != null) {
//            modelMap.addAttribute("personnelList", personnelService.findByKeyword(keyword));
//        } else {
//            modelMap.addAttribute("personnelList", personnelService.getAllPersonnel(1, 100));
//        }
//        return "personnel";
//    }

    @GetMapping("/personnel")
    public String personnel(ModelMap modelMap) {
        modelMap.addAttribute("personnelList", personnelService.getAllPersonnel(1, 100));
        return "personnel";

    }

    @GetMapping("/personnel/{id}")
    public String personnel(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("personnel", personnelService.getPersonnelById(id).get());
        return "one-personnel";
    }

    @PostMapping("/personnel/{id}")
    public String updatePersonnel(@Valid @ModelAttribute("personnel") Personnel personnel,
                                  @PathVariable Long id, final Errors errors) {
        if (errors.hasErrors()) {
            return "one-personnel";
        }
        personnelService.updatePersonnelById(id, personnel);
        return "redirect:/personnel/" + id;
    }


    @GetMapping("/personnel/add")
    public String showPersonnelAdd(ModelMap modelMap) {
        modelMap.addAttribute("personnel", new Personnel());
        modelMap.addAttribute("error-msg", "błąd danych");
        return "personnel-add";
    }

    @PostMapping("/personnel/add")
    public String addPersonnel(@Valid @ModelAttribute("personnel") Personnel personnel, final Errors error) {
        if (error.hasErrors()) {
            return "personnel-add";
        }
        if (personnel.getFirstName().equals("Bankowy")) {
            throw new RuntimeException("Błąd!");
        }
        personnelService.createNewPersonnel(personnel);
        return "succeded";
    }

    @PostMapping("/personnel/delete/{id}")
    public String deletePersonnel(@PathVariable Long id) {
        personnelService.removePersonnelById(id);
        return "redirect:/personnel";
    }

    @GetMapping("/personnel/delete/{id}")
    public String getDelete(@PathVariable Long id) {
        personnelService.getPersonnelById(id);
        return "one-personnel";
    }


}
