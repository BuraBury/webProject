package com.project.demo.controller;

import com.project.demo.model.Client;
import com.project.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Client> client = clientService.getClientById(id);
        if (Objects.nonNull(client)) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(clientService.getAllClients(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        if (clientService.removeClientById(id)) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createClient(@RequestBody List<Client> clients) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createBatchOfClients(clients));
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createNewClient(client));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateClientById(@PathVariable Long id, @RequestBody Client client) {
        if (Objects.nonNull(clientService.getClientById(id))) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(clientService.updateClientById(id, client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
