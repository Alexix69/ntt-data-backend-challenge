package com.ntt_data.backend_challenge.client_person.application.mapper;

import com.ntt_data.backend_challenge.client_person.application.dto.ClientDTO;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    ClientEntity toEntity(ClientDTO clientDTO);

    @Mapping(target = "name", source = "person.name")
    @Mapping(target = "gender", source = "person.gender")
    @Mapping(target = "age", source = "person.age")
    @Mapping(target = "identification", source = "person.identification")
    @Mapping(target = "address", source = "person.address")
    @Mapping(target = "phone", source = "person.phone")
    @Mapping(target = "password", ignore = true)
    ClientDTO toDTO(ClientEntity client);

    @Mapping(target = "id", ignore = true)
    void updateClientFromDto(ClientDTO clientDTO, @MappingTarget ClientEntity client);
}
