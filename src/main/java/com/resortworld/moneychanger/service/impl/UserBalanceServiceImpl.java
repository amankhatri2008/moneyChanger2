package com.resortworld.moneychanger.service.impl;

import com.resortworld.moneychanger.domain.UserBalance;
import com.resortworld.moneychanger.repository.UserBalanceRepository;
import com.resortworld.moneychanger.service.UserBalanceService;


import com.resortworld.moneychanger.service.dto.UserBalanceDTO;
import com.resortworld.moneychanger.service.mapper.UserBalanceMapper;

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
 * Service Implementation for managing {@link UserBalance}.
 */
@Service
@Transactional
public class UserBalanceServiceImpl implements UserBalanceService {

    private final Logger log = LoggerFactory.getLogger(UserBalanceServiceImpl.class);

    private final UserBalanceRepository userBalanceRepository;

    private final UserBalanceMapper userBalanceMapper;

    public UserBalanceServiceImpl(UserBalanceRepository userBalanceRepository, UserBalanceMapper userBalanceMapper) {
        this.userBalanceRepository = userBalanceRepository;
        this.userBalanceMapper = userBalanceMapper;
    }

    /**
     * Save a userBalance.
     *
     * @param userBalanceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserBalanceDTO save(UserBalanceDTO userBalanceDTO) {
        log.debug("Request to save UserBalance : {}", userBalanceDTO);
        UserBalance userBalance = userBalanceMapper.toEntity(userBalanceDTO);
        userBalance = userBalanceRepository.save(userBalance);
        return userBalanceMapper.toDto(userBalance);
    }

    /**
     * Get all the userBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserBalanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserBalances");
        return userBalanceRepository.findAll(pageable)
            .map(userBalanceMapper::toDto);
    }



    /**
    *  Get all the userBalances where CurrencyMany is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserBalanceDTO> findAllWhereCurrencyManyIsNull() {
        log.debug("Request to get all userBalances where CurrencyMany is null");
        return StreamSupport
            .stream(userBalanceRepository.findAll().spliterator(), false)
            .filter(userBalance -> userBalance.getCurrencyMany() == null)
            .map(userBalanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userBalance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserBalanceDTO> findOne(Long id) {
        log.debug("Request to get UserBalance : {}", id);
        return userBalanceRepository.findById(id)
            .map(userBalanceMapper::toDto);
    }

    /**
     * Delete the userBalance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserBalance : {}", id);
        userBalanceRepository.deleteById(id);
    }
}
