package com.api.client;

import com.api.client.mapper.ClientMapper;
import com.api.client.model.ApiClientInfo;
import com.api.client.repository.ClientRepository;
import com.api.client.repository.PersonRepository;
import com.api.client.service.impl.ClientServiceImpl;
import com.api.core.starter.security.ApiNotFoundException;
import com.api.persistence.domain.Client;
import com.api.util.Constants;
import com.api.util.ConstantsMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdentity_ClientExists_ReturnsClientInfo() {
        // Arrange
        String identity = "1130640371";

        Client client = new Client();
        client.setIdentity(identity);
        client.setId(1);
        client.setName("Diego Hernandez");

        ApiClientInfo apiClientInfo = new ApiClientInfo();
        apiClientInfo.setIdentity("1130640371");
        apiClientInfo.setName("Diego Hernandez");

        when(personRepository.findByIdentity(identity)).thenReturn(java.util.Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(java.util.Optional.of(client));
        when(clientMapper.mapClientToApiClientInfo(client)).thenReturn(apiClientInfo);

        ApiClientInfo result = clientService.findByIdentity(identity);

                assertNotNull(result);
        assertEquals("1130640371", result.getIdentity());
        assertEquals("Diego Hernandez", result.getName());

        verify(personRepository, times(1)).findByIdentity(identity);
        verify(clientRepository, times(1)).findById(client.getId());
    }

    @Test
    void testFindByIdentity_ClientNotFound_ThrowsApiNotFoundException() {
        // Arrange
        String identity = "1130640371";
        when(personRepository.findByIdentity(identity)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ApiNotFoundException exception = assertThrows(ApiNotFoundException.class, () -> {
            clientService.findByIdentity(identity);
        });

        assertEquals(Constants.ERROR_CLIENT_NOT_FOUND, exception.getCode());
        assertEquals(ConstantsMessage.MESSAGE_CLIENT_NOT_FOUND, exception.getMessage());

        verify(personRepository, times(1)).findByIdentity(identity);
        verify(clientRepository, times(0)).findById(any());
    }
}
