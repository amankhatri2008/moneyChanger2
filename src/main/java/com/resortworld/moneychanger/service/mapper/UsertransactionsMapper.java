package com.resortworld.moneychanger.service.mapper;

import com.resortworld.moneychanger.domain.*;
import com.resortworld.moneychanger.service.dto.UsertransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usertransactions} and its DTO {@link UsertransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UsertransactionsMapper extends EntityMapper<UsertransactionsDTO, Usertransactions> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UsertransactionsDTO toDto(Usertransactions usertransactions);

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "to", ignore = true)
    @Mapping(source = "userId", target = "user")
    Usertransactions toEntity(UsertransactionsDTO usertransactionsDTO);

    default Usertransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usertransactions usertransactions = new Usertransactions();
        usertransactions.setId(id);
        return usertransactions;
    }
}
