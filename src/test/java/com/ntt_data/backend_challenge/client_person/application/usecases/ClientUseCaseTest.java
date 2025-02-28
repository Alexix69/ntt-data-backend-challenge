package com.ntt_data.backend_challenge.client_person.application.usecases;

import com.ntt_data.backend_challenge.client_person.application.dto.ClientDTO;
import com.ntt_data.backend_challenge.client_person.application.mapper.ClientMapper;
import com.ntt_data.backend_challenge.client_person.application.mapper.PersonMapper;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import com.ntt_data.backend_challenge.client_person.domain.PersonEntity;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.ClientRepository;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.PersonRepository;
import com.ntt_data.backend_challenge.common.exceptions.UniqueConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ Asegura que Mockito se inicializa correctamente
class ClientUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PersonMapper personMapper; // ✅ Se añade el mock para evitar NullPointerException

    @InjectMocks
    private ClientUseCase clientUseCase;

    private ClientDTO clientDTO;
    private ClientEntity clientEntity;
    private PersonEntity personEntity;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO(1L, "John Doe", "Male", 30, "123456789", "Main St", "555-1234", "password123", true);

        personEntity = new PersonEntity();
        personEntity.setId(1L);
        personEntity.setName("John Doe");
        personEntity.setGender("Male");
        personEntity.setAge(30);
        personEntity.setIdentification("123456789");
        personEntity.setAddress("Main St");
        personEntity.setPhone("555-1234");

        clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setPerson(personEntity);
        clientEntity.setPassword("password123");
        clientEntity.setStatus(true);
    }

    @Test
    void shouldCreateClientSuccessfully() {
        when(personRepository.findByIdentification(clientDTO.getIdentification())).thenReturn(Optional.empty());
        when(personMapper.toEntity(clientDTO)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(personEntity); // ✅ Se mockea solo lo necesario
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.toDTO(clientEntity)).thenReturn(clientDTO);

        ClientDTO createdClient = clientUseCase.createClient(clientDTO);

        assertNotNull(createdClient);
        assertEquals(clientDTO.getIdentification(), createdClient.getIdentification());
        verify(personRepository, times(1)).save(personEntity);
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenIdentificationAlreadyExists() {
        when(personRepository.findByIdentification(clientDTO.getIdentification())).thenReturn(Optional.of(personEntity));

        Exception exception = assertThrows(UniqueConstraintViolationException.class, () -> {
            clientUseCase.createClient(clientDTO);
        });

        assertEquals("The value '123456789' already exists for the field 'identification'", exception.getMessage());
        verify(clientRepository, never()).save(any(ClientEntity.class));
    }
}
