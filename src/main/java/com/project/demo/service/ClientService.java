package com.project.demo.service;

import com.project.demo.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> getClientById(Long id);

    List<Client> getAllClients(Integer page, Integer size);

    boolean removeClientById(Long id);

    Client createNewClient(Client client);

    List<Client> createBatchOfClients(List<Client> clients);

    Client updateClientById(Long id, Client client);
}
