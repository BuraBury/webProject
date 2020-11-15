package com.project.demo.service;

import com.project.demo.model.Client;

import java.util.List;

public interface ClientService {

    Client getClientById(Long id);
    List<Client> getAllClients();
    boolean removeClientById(Long id);
    Client createNewClient(Client client);
    List<Client> createBatchOfClients(List<Client> clients);
    Client updateClientById(Long id, Client client);
}
