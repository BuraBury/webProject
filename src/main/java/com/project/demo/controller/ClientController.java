package com.project.demo.controller;

import com.project.demo.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/hotel/client")
@Slf4j
public class ClientController {

    //create new Client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        log.info(client.toString());
        client.setId(client.getId());
        return client;
    }
}
