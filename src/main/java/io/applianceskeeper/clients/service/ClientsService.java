package io.applianceskeeper.clients.service;

import io.applianceskeeper.clients.ClientStillReferencedException;
import io.applianceskeeper.clients.NoSuchClientFoundException;
import io.applianceskeeper.clients.data.ClientsRepository;
import io.applianceskeeper.clients.models.Client;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    private final ClientsRepository repository;

    ClientsService(ClientsRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAllClients() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Client> getClientById(Long id) {
        return repository.findById(id);
    }

    public Client saveClient(Client client) {
        return repository.save(client);
    }

    public void deleteClient(Long id) throws NoSuchClientFoundException, NullPointerException, ClientStillReferencedException {
        if (id != null)
            try {
                repository.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                throw new NoSuchClientFoundException("No client found with id: " + id);
            } catch (DataIntegrityViolationException e) {
                System.out.println(e.getMessage());
                throw new ClientStillReferencedException();
            }
        else
            throw new NullPointerException("No client find with id: null");
    }

    public List<Client> findAllBySearchTerm(String searchTerm) {
        return repository.findAllBySearchTerm(searchTerm);
    }
}
