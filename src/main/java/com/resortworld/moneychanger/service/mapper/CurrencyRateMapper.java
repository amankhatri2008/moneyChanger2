package com.resortworld.moneychanger.service.mapper;

import com.resortworld.moneychanger.domain.*;
import com.resortworld.moneychanger.service.dto.CurrencyRateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrencyRate} and its DTO {@link CurrencyRateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyRateMapper extends EntityMapper<CurrencyRateDTO, CurrencyRate> {



    default CurrencyRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setId(id);
        return currencyRate;
    }
}
