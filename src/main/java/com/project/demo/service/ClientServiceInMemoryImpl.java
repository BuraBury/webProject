package com.project.demo.service;

import com.project.demo.model.Client;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class ClientServiceInMemoryImpl implements ClientService {

    private final Map<Long, Client> clientMap = new HashMap<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        clientMap.put(nextId, Client.builder()
                .id(nextId)
                .firstName("Pierwszy")
                .lastName("Klient")
                .arrivalDate(LocalDate.parse("2020-03-01"))
                .departureDate(LocalDate.parse("2020-03-18"))
                .passportNumber("123456")
                .roomNumber("1")
                .build());
        nextId++;
    }

    @Override
    public Client getClientById(Long id) {
        return clientMap.getOrDefault(id, null);
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clientMap.values());
    }

    @Override
    public boolean removeClientById(Long id) {
        if (clientMap.containsKey(id)) {
            clientMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Client createNewClient(Client client) {
        client.setId(getNextId());
        return clientMap.put(client.getId(), client);
    }

    @Override
    public List<Client> createBatchOfClients(List<Client> clients) {
        return addClient(clients);
    }

    @Override
    public Client updateClientById(Long id, Client client) {
        if (clientMap.containsKey(id)) {
            if (client.getFirstName() != null) {
                clientMap.get(id).setFirstName(client.getFirstName());
            }
            if (client.getLastName() != null) {
                clientMap.get(id).setLastName(client.getLastName());
            }
            if (client.getArrivalDate() != null) {
                clientMap.get(id).setArrivalDate(client.getArrivalDate());
            }
            if (client.getDepartureDate() != null) {
                clientMap.get(id).setDepartureDate(client.getDepartureDate());
            }
            if (client.getPassportNumber() != null) {
                clientMap.get(id).setPassportNumber(client.getPassportNumber());
            }
            return clientMap.get(id);
        }
        return null;
    }

    private List<Client> addClient(List<Client> clients) {
        clients.forEach(client -> {
            client.setId(getNextId());
            clientMap.put(client.getId(), client);
        });

        return clients;
    }

    private Long getNextId() {
        return nextId++;
    }

}


