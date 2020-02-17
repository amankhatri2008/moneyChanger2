package com.resortworld.moneychanger.web.rest;

import com.resortworld.moneychanger.service.UserBalanceService;
import com.resortworld.moneychanger.service.UserBalanceService;
import com.resortworld.moneychanger.web.rest.errors.BadRequestAlertException;
import com.resortworld.moneychanger.service.dto.UserBalanceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.resortworld.moneychanger.domain.UserBalance}.
 */
@RestController
@RequestMapping("/api")
public class UserBalanceResource {

    private final Logger log = LoggerFactory.getLogger(UserBalanceResource.class);

    private static final String ENTITY_NAME = "userBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBalanceService userBalanceService;

    public UserBalanceResource(UserBalanceService userBalanceService) {
        this.userBalanceService = userBalanceService;
    }

    /**
     * {@code POST  /userBalances} : Create a new userBalance.
     *
     * @param userBalanceDTO the userBalanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBalanceDTO, or with status {@code 400 (Bad Request)} if the userBalance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/userBalances")
    public ResponseEntity<UserBalanceDTO> createUserBalance(@Valid @RequestBody UserBalanceDTO userBalanceDTO) throws URISyntaxException {
        log.debug("REST request to save UserBalance : {}", userBalanceDTO);
        if (userBalanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new userBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBalanceDTO result = userBalanceService.save(userBalanceDTO);
        return ResponseEntity.created(new URI("/api/userBalances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /userBalances} : Updates an existing userBalance.
     *
     * @param userBalanceDTO the userBalanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBalanceDTO,
     * or with status {@code 400 (Bad Request)} if the userBalanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBalanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/userBalances")
    public ResponseEntity<UserBalanceDTO> updateUserBalance(@Valid @RequestBody UserBalanceDTO userBalanceDTO) throws URISyntaxException {
        log.debug("REST request to update UserBalance : {}", userBalanceDTO);
        if (userBalanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBalanceDTO result = userBalanceService.save(userBalanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userBalanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /userBalances} : get all the userBalances.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBalances in body.
     */
    @GetMapping("/userBalances")
    public ResponseEntity<List<UserBalanceDTO>> getAllUserBalances(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("currencymany-is-null".equals(filter)) {
            log.debug("REST request to get all UserBalances where currencyMany is null");
            return new ResponseEntity<>(userBalanceService.findAllWhereCurrencyManyIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserBalances");
        Page<UserBalanceDTO> page = userBalanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /userBalances/:id} : get the "id" userBalance.
     *
     * @param id the id of the userBalanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBalanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/userBalances/{id}")
    public ResponseEntity<UserBalanceDTO> getUserBalance(@PathVariable Long id) {
        log.debug("REST request to get UserBalance : {}", id);
        Optional<UserBalanceDTO> userBalanceDTO = userBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBalanceDTO);
    }

    /**
     * {@code DELETE  /userBalances/:id} : delete the "id" userBalance.
     *
     * @param id the id of the userBalanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/userBalances/{id}")
    public ResponseEntity<Void> deleteUserBalance(@PathVariable Long id) {
        log.debug("REST request to delete UserBalance : {}", id);
        userBalanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
