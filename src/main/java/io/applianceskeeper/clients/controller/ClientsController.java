package io.applianceskeeper.clients.controller;

import io.applianceskeeper.clients.ClientStillReferencedException;
import io.applianceskeeper.clients.NoSuchClientFoundException;
import io.applianceskeeper.clients.models.Client;
import io.applianceskeeper.clients.service.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping()
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.of(clientsService.getClientById(id));
    }

    @GetMapping(params = {"searchTerm"})
    public ResponseEntity<List<Client>> findAllBySearchTerm(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(clientsService.findAllBySearchTerm(searchTerm));
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        var clientFromBackend = clientsService.saveClient(client);
        var link = linkTo(ClientsController.class).slash(client.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(link.getHref())).body(clientFromBackend);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientsService.saveClient(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
            clientsService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchClientFoundException | NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ClientStillReferencedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
