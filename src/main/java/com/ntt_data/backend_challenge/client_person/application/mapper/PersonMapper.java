package com.ntt_data.backend_challenge.client_person.application.mapper;

import com.ntt_data.backend_challenge.client_person.application.dto.ClientDTO;
import com.ntt_data.backend_challenge.client_person.domain.PersonEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonEntity toEntity(ClientDTO clientDTO);

    ClientDTO toDTO(PersonEntity person);

    @Mapping(target = "id", ignore = true)
    void updatePersonFromDto(ClientDTO clientDTO, @MappingTarget PersonEntity person);
}

