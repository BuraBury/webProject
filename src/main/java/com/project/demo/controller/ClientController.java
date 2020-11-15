package com.project.demo.controller;

import com.project.demo.model.Client;
import com.project.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/hotel/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (Objects.nonNull(client)) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //create new Client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        log.info(client.toString());
        client.setId(client.getId());
        return client;
    }
}
