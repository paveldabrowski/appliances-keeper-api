package io.applianceskeeper.clients.service;

import io.applianceskeeper.clients.ClientStillReferencedException;
import io.applianceskeeper.clients.NoSuchClientFoundException;
import io.applianceskeeper.clients.data.ClientsRepository;
import io.applianceskeeper.clients.models.Client;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ClientsServiceTest {

    @InjectMocks
    private ClientsService clientsService;
    @Mock
    private ClientsRepository repository;

    @Test
    void shouldDeleteClient() throws NoSuchClientFoundException, ClientStillReferencedException {
        //given
        Client client = new Client();
        client.setId(1L);
        doNothing().when(repository).deleteById((client.getId()));

        //when
        clientsService.deleteClient(client.getId());

        //then
        then(repository).should(times(1)).deleteById(client.getId());
    }

    @Test
    void shouldNullPointerExceptionWhenClientIdEqualNull() {
        //given
        Client client = new Client();
            Long id = client.getId();

        //when + then
        assertThrows(NullPointerException.class, () -> {
            clientsService.deleteClient(id);
        });
    }

    @Test
    void shouldThrowNoSuchClientExceptionWhenClientIsNotFoundInDatabase() {
       //given
        Client client = new Client();
        client.setId(1L);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(client.getId());

        //when + then
        assertThrows(NoSuchClientFoundException.class, () -> clientsService.deleteClient(client.getId()));
    }
}
