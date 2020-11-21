package com.project.demo.service;

import com.project.demo.model.Client;
import com.project.demo.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
@Slf4j
@Primary
public class ClientServiceDbImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceDbImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean removeClientById(Long id) {
        clientRepository.deleteById(id);
        return true;
    }

    @Override
    public Client createNewClient(Client client) {
        log.info("Tworze nowego klienta");
        return clientRepository.save(client);
    }

    @Override
    public List<Client> createBatchOfClients(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }

    @Override
    public Client updateClientById(Long id, Client client) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            return clientRepository.save(client);
        }
        return null;
    }
}
