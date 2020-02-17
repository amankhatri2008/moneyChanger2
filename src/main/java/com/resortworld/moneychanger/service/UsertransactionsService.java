package com.resortworld.moneychanger.service;

import com.resortworld.moneychanger.service.dto.UsertransactionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resortworld.moneychanger.domain.Usertransactions}.
 */
public interface UsertransactionsService {

    /**
     * Save a usertransactions.
     *
     * @param usertransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    UsertransactionsDTO save(UsertransactionsDTO usertransactionsDTO);

    /**
     * Get all the usertransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsertransactionsDTO> findAll(Pageable pageable);
    /**
     * Get all the UsertransactionsDTO where From is {@code null}.
     *
     * @return the list of entities.
     */
    List<UsertransactionsDTO> findAllWhereFromIsNull();
    /**
     * Get all the UsertransactionsDTO where To is {@code null}.
     *
     * @return the list of entities.
     */
    List<UsertransactionsDTO> findAllWhereToIsNull();


    /**
     * Get the "id" usertransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsertransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" usertransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
