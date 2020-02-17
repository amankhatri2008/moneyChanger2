package com.resortworld.moneychanger.service.impl;

import com.resortworld.moneychanger.domain.enumeration.Action;
import com.resortworld.moneychanger.service.UsertransactionsService;
import com.resortworld.moneychanger.domain.Usertransactions;
import com.resortworld.moneychanger.repository.UsertransactionsRepository;
import com.resortworld.moneychanger.service.dto.UsertransactionsDTO;
import com.resortworld.moneychanger.service.mapper.UsertransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Usertransactions}.
 */
@Service
@Transactional
public class UsertransactionsServiceImpl implements UsertransactionsService {

    private final Logger log = LoggerFactory.getLogger(UsertransactionsServiceImpl.class);

    private final UsertransactionsRepository usertransactionsRepository;

    private final CurrencyRateServiceImpl currencyRateService;

    private final UsertransactionsMapper usertransactionsMapper;

    public UsertransactionsServiceImpl(UsertransactionsRepository usertransactionsRepository, UsertransactionsMapper usertransactionsMapper,CurrencyRateServiceImpl currencyRateService) {
        this.usertransactionsRepository = usertransactionsRepository;
        this.usertransactionsMapper = usertransactionsMapper;
        this.currencyRateService=currencyRateService;
    }

    /**
     * Save a usertransactions.
     *
     * @param usertransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsertransactionsDTO save(UsertransactionsDTO usertransactionsDTO) {
        log.debug("Request to save Usertransactions : {}", usertransactionsDTO);
        Usertransactions usertransactions = usertransactionsMapper.toEntity(usertransactionsDTO);
        usertransactions = usertransactionsRepository.save(usertransactions);
        return usertransactionsMapper.toDto(usertransactions);
    }

    /**
     * Get all the usertransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsertransactionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usertransactions");
        return usertransactionsRepository.findAll(pageable)
            .map(usertransactionsMapper::toDto);
    }



    /**
    *  Get all the usertransactions where From is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsertransactionsDTO> findAllWhereFromIsNull() {
        log.debug("Request to get all usertransactions where From is null");
        return StreamSupport
            .stream(usertransactionsRepository.findAll().spliterator(), false)
            .filter(usertransactions -> usertransactions.getFrom() == null)
            .map(usertransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the usertransactions where To is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsertransactionsDTO> findAllWhereToIsNull() {
        log.debug("Request to get all usertransactions where To is null");
        return StreamSupport
            .stream(usertransactionsRepository.findAll().spliterator(), false)
            .filter(usertransactions -> usertransactions.getTo() == null)
            .map(usertransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one usertransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsertransactionsDTO> findOne(Long id) {
        log.debug("Request to get Usertransactions : {}", id);
        return usertransactionsRepository.findById(id)
            .map(usertransactionsMapper::toDto);
    }

    /**
     * Delete the usertransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usertransactions : {}", id);
        usertransactionsRepository.deleteById(id);
    }
}
