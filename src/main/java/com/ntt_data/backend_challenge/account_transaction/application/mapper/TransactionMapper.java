package com.ntt_data.backend_challenge.account_transaction.application.mapper;

import com.ntt_data.backend_challenge.account_transaction.application.dto.TransactionDTO;
import com.ntt_data.backend_challenge.account_transaction.domain.TransactionEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "accountId", source = "account.id")
    TransactionDTO toDTO(TransactionEntity transaction);

    @Mapping(target = "id", ignore = true)
    TransactionEntity toEntity(TransactionDTO transactionDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTransactionFromDto(TransactionDTO transactionDTO, @MappingTarget TransactionEntity transaction);
}

