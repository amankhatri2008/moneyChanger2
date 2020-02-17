package com.resortworld.moneychanger.web.rest;

import com.resortworld.moneychanger.service.UsertransactionsService;
import com.resortworld.moneychanger.web.rest.errors.BadRequestAlertException;
import com.resortworld.moneychanger.service.dto.UsertransactionsDTO;

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
 * REST controller for managing {@link com.resortworld.moneychanger.domain.Usertransactions}.
 */
@RestController
@RequestMapping("/api")
public class UsertransactionsResource {

    private final Logger log = LoggerFactory.getLogger(UsertransactionsResource.class);

    private static final String ENTITY_NAME = "usertransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsertransactionsService usertransactionsService;

    public UsertransactionsResource(UsertransactionsService usertransactionsService) {
        this.usertransactionsService = usertransactionsService;
    }

    /**
     * {@code POST  /usertransactions} : Create a new usertransactions.
     *
     * @param usertransactionsDTO the usertransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usertransactionsDTO, or with status {@code 400 (Bad Request)} if the usertransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usertransactions")
    public ResponseEntity<UsertransactionsDTO> createUsertransactions(@Valid @RequestBody UsertransactionsDTO usertransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save Usertransactions : {}", usertransactionsDTO);
        if (usertransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new usertransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsertransactionsDTO result = usertransactionsService.save(usertransactionsDTO);
        return ResponseEntity.created(new URI("/api/usertransactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usertransactions} : Updates an existing usertransactions.
     *
     * @param usertransactionsDTO the usertransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usertransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the usertransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usertransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usertransactions")
    public ResponseEntity<UsertransactionsDTO> updateUsertransactions(@Valid @RequestBody UsertransactionsDTO usertransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update Usertransactions : {}", usertransactionsDTO);
        if (usertransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsertransactionsDTO result = usertransactionsService.save(usertransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usertransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usertransactions} : get all the usertransactions.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usertransactions in body.
     */
    @GetMapping("/usertransactions")
    public ResponseEntity<List<UsertransactionsDTO>> getAllUsertransactions(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("from-is-null".equals(filter)) {
            log.debug("REST request to get all Usertransactionss where from is null");
            return new ResponseEntity<>(usertransactionsService.findAllWhereFromIsNull(),
                    HttpStatus.OK);
        }
        if ("to-is-null".equals(filter)) {
            log.debug("REST request to get all Usertransactionss where to is null");
            return new ResponseEntity<>(usertransactionsService.findAllWhereToIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Usertransactions");
        Page<UsertransactionsDTO> page = usertransactionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usertransactions/:id} : get the "id" usertransactions.
     *
     * @param id the id of the usertransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usertransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usertransactions/{id}")
    public ResponseEntity<UsertransactionsDTO> getUsertransactions(@PathVariable Long id) {
        log.debug("REST request to get Usertransactions : {}", id);
        Optional<UsertransactionsDTO> usertransactionsDTO = usertransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usertransactionsDTO);
    }

    /**
     * {@code DELETE  /usertransactions/:id} : delete the "id" usertransactions.
     *
     * @param id the id of the usertransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usertransactions/{id}")
    public ResponseEntity<Void> deleteUsertransactions(@PathVariable Long id) {
        log.debug("REST request to delete Usertransactions : {}", id);
        usertransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
