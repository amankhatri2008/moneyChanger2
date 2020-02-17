package com.resortworld.moneychanger.service.mapper;

import com.resortworld.moneychanger.domain.*;
import com.resortworld.moneychanger.service.dto.UserBalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserBalance} and its DTO {@link UserBalanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserBalanceMapper extends EntityMapper<UserBalanceDTO, UserBalance> {

    @Mapping(source = "name.id", target = "nameId")
    @Mapping(source = "name.login", target = "nameLogin")
    UserBalanceDTO toDto(UserBalance userBalance);

    @Mapping(source = "nameId", target = "name")
    @Mapping(target = "currencyMany", ignore = true)
    UserBalance toEntity(UserBalanceDTO userBalanceDTO);

    default UserBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        userBalance.setId(id);
        return userBalance;
    }
}
