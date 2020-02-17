package com.resortworld.moneychanger.service;

import com.resortworld.moneychanger.service.dto.UserBalanceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resortworld.moneychanger.domain.UserBalance}.
 */
public interface UserBalanceService {

    /**
     * Save a userBalance.
     *
     * @param userBalanceDTO the entity to save.
     * @return the persisted entity.
     */
    UserBalanceDTO save(UserBalanceDTO userBalanceDTO);

    /**
     * Get all the userBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserBalanceDTO> findAll(Pageable pageable);
    /**
     * Get all the UserBalanceDTO where CurrencyMany is {@code null}.
     *
     * @return the list of entities.
     */
    List<UserBalanceDTO> findAllWhereCurrencyManyIsNull();


    /**
     * Get the "id" userBalance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserBalanceDTO> findOne(Long id);

    /**
     * Delete the "id" userBalance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
