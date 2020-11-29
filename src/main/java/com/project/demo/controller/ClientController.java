package com.project.demo.controller;

import com.project.demo.model.Client;
import com.project.demo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/client")
    public String client(ModelMap modelMap) {
        modelMap.addAttribute("clientList", clientService.getAllClients(1, 100));
        return "client";
    }

    @GetMapping("/client/{id}")
    public String client(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("client", clientService.getClientById(id).get());
        return "one-client";
    }

    @GetMapping("/client/add")
    public String showClientAdd(ModelMap modelMap) {
        modelMap.addAttribute("client", new Client());
        modelMap.addAttribute("error-msg", "błąd danych");
        return "client-add";
    }

    @PostMapping("/client/{id}")
    public String updateClient(@Valid @ModelAttribute("client") Client client, @PathVariable Long id, final Errors errors) {
        if (errors.hasErrors()) {
            return "one-client";
        }
        clientService.updateClientById(id, client);
        return "redirect:/client/" + id;
    }

    @PostMapping("/client/add")
    public String addClient(@Valid @ModelAttribute("client") Client client, final Errors error) {
        if (error.hasErrors()) {
            return "client-add";
        }
        if (client.getFirstName().equals("Bankowy")) {
            throw new RuntimeException("Błąd!");
        }
        clientService.createNewClient(client);
        return "redirect:/";
    }
}
