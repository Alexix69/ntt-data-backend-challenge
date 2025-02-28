package com.ntt_data.backend_challenge.client_person.application.usecases;

import com.ntt_data.backend_challenge.client_person.application.dto.ClientDTO;
import com.ntt_data.backend_challenge.client_person.application.mapper.ClientMapper;
import com.ntt_data.backend_challenge.client_person.application.mapper.PersonMapper;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import com.ntt_data.backend_challenge.client_person.domain.PersonEntity;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.ClientRepository;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.PersonRepository;
import com.ntt_data.backend_challenge.common.exceptions.UniqueConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientUseCase {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    /*public ClientUseCase(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }*/

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(clientMapper::toDTO).collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow();
        return clientMapper.toDTO(client);
    }

    /*public Optional<ClientDTO> getClientByIdentification(String identification) {
        return clientRepository.findByIdentification(identification)
                .map(clientMapper::toDTO);
    }*/

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (personRepository.findByIdentification(clientDTO.getIdentification()).isPresent()) {
            throw new UniqueConstraintViolationException("identification", clientDTO.getIdentification());
        }
        // Guardar la persona primero
        PersonEntity person = personMapper.toEntity(clientDTO);
        person = personRepository.save(person); // Forzar sincronización con la BD

        // Guardar el cliente con el ID de la persona
        ClientEntity client = ClientEntity.builder().person(person).password(clientDTO.getPassword()).status(clientDTO.getStatus()).build();


        return clientMapper.toDTO(clientRepository.save(client));
    }


    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        ClientEntity client = clientRepository.findById(id).orElseThrow();
        personRepository.findByIdentification(clientDTO.getIdentification()).ifPresent(existingPerson -> {
            if (!existingPerson.getId().equals(client.getPerson().getId())) {
                throw new UniqueConstraintViolationException("identification", clientDTO.getIdentification());
            }
        });

        PersonEntity person = client.getPerson();
        personMapper.updatePersonFromDto(clientDTO, person);
        personRepository.save(person); // Guardar cambios en Persona
        clientMapper.updateClientFromDto(clientDTO, client);
        return clientMapper.toDTO(clientRepository.save(client));

    }


    @Transactional
    public void deleteClient(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);
    }
}
