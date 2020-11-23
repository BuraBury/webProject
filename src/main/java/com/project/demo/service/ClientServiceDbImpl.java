package com.project.demo.service;

import com.project.demo.exceptions.WrongPageException;
import com.project.demo.exceptions.WrongIdException;
import com.project.demo.model.Client;
import com.project.demo.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<Client> getClientById(Long id) throws WrongIdException {
        if (clientRepository.findById(id).isEmpty()) {
            log.info("Podane id klienta nie istnieje w bazie; id = " + id);
            throw new WrongIdException("Błędne id klienta; id = " + id);
        } else {
            log.info("Odnaleziono klienta po id; id = " + id);
            return clientRepository.findById(id);
        }

    }

    @Override
    public List<Client> getAllClients(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 1) {
            throw new WrongPageException("Strona nie moze byc mniejsza niz 1");
        }

        Sort sort = Sort.by("lastName").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return clientRepository.findAll(pageable).getContent();
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
