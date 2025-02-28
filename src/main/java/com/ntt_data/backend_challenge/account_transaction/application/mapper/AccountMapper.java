package com.ntt_data.backend_challenge.account_transaction.application.mapper;

import com.ntt_data.backend_challenge.account_transaction.application.dto.AccountDTO;
import com.ntt_data.backend_challenge.account_transaction.domain.AccountEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "clientId", source = "client.id")
    AccountDTO toDTO(AccountEntity account);

    @Mapping(target = "id", ignore = true)
    AccountEntity toEntity(AccountDTO accountDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountFromDto(AccountDTO accountDTO, @MappingTarget AccountEntity account);
}

