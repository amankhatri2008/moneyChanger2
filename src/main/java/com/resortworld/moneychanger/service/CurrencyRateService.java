package com.resortworld.moneychanger.service;

import com.resortworld.moneychanger.service.dto.CurrencyRateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.resortworld.moneychanger.domain.CurrencyRate}.
 */
public interface CurrencyRateService {

    /**
     * Save a currencyRate.
     *
     * @param currencyRateDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyRateDTO save(CurrencyRateDTO currencyRateDTO);

    /**
     * Get all the currencyRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyRateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" currencyRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyRateDTO> findOne(Long id);

    /**
     * Delete the "id" currencyRate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
