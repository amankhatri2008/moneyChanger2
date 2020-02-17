package com.resortworld.moneychanger.web.rest;

import com.resortworld.moneychanger.MoneyChangerApp;
import com.resortworld.moneychanger.domain.Usertransactions;
import com.resortworld.moneychanger.domain.User;
import com.resortworld.moneychanger.repository.UsertransactionsRepository;
import com.resortworld.moneychanger.service.UsertransactionsService;
import com.resortworld.moneychanger.service.dto.UsertransactionsDTO;
import com.resortworld.moneychanger.service.mapper.UsertransactionsMapper;
import com.resortworld.moneychanger.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.resortworld.moneychanger.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.resortworld.moneychanger.domain.enumeration.Action;
/**
 * Integration tests for the {@link UsertransactionsResource} REST controller.
 */
@SpringBootTest(classes = MoneyChangerApp.class)
public class UsertransactionsResourceIT {

    private static final BigDecimal DEFAULT_FROM_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_FROM_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TO_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TO_QUANTITY = new BigDecimal(2);

    private static final Action DEFAULT_ACTION = Action.BUY;
    private static final Action UPDATED_ACTION = Action.SELL;

    @Autowired
    private UsertransactionsRepository usertransactionsRepository;

    @Autowired
    private UsertransactionsMapper usertransactionsMapper;

    @Autowired
    private UsertransactionsService usertransactionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUsertransactionsMockMvc;

    private Usertransactions usertransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsertransactionsResource usertransactionsResource = new UsertransactionsResource(usertransactionsService);
        this.restUsertransactionsMockMvc = MockMvcBuilders.standaloneSetup(usertransactionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usertransactions createEntity(EntityManager em) {
        Usertransactions usertransactions = new Usertransactions()
            .from_quantity(DEFAULT_FROM_QUANTITY)
            .toQuantity(DEFAULT_TO_QUANTITY)
            .action(DEFAULT_ACTION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        usertransactions.setUser(user);
        return usertransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usertransactions createUpdatedEntity(EntityManager em) {
        Usertransactions usertransactions = new Usertransactions()
            .from_quantity(UPDATED_FROM_QUANTITY)
            .toQuantity(UPDATED_TO_QUANTITY)
            .action(UPDATED_ACTION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        usertransactions.setUser(user);
        return usertransactions;
    }

    @BeforeEach
    public void initTest() {
        usertransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsertransactions() throws Exception {
        int databaseSizeBeforeCreate = usertransactionsRepository.findAll().size();

        // Create the Usertransactions
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(usertransactions);
        restUsertransactionsMockMvc.perform(post("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Usertransactions in the database
        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        Usertransactions testUsertransactions = usertransactionsList.get(usertransactionsList.size() - 1);
        assertThat(testUsertransactions.getFrom_quantity()).isEqualTo(DEFAULT_FROM_QUANTITY);
        assertThat(testUsertransactions.getToQuantity()).isEqualTo(DEFAULT_TO_QUANTITY);
        assertThat(testUsertransactions.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    public void createUsertransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usertransactionsRepository.findAll().size();

        // Create the Usertransactions with an existing ID
        usertransactions.setId(1L);
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(usertransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsertransactionsMockMvc.perform(post("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usertransactions in the database
        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFrom_quantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = usertransactionsRepository.findAll().size();
        // set the field null
        usertransactions.setFrom_quantity(null);

        // Create the Usertransactions, which fails.
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(usertransactions);

        restUsertransactionsMockMvc.perform(post("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = usertransactionsRepository.findAll().size();
        // set the field null
        usertransactions.setToQuantity(null);

        // Create the Usertransactions, which fails.
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(usertransactions);

        restUsertransactionsMockMvc.perform(post("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsertransactions() throws Exception {
        // Initialize the database
        usertransactionsRepository.saveAndFlush(usertransactions);

        // Get all the usertransactionsList
        restUsertransactionsMockMvc.perform(get("/api/usertransactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usertransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].from_quantity").value(hasItem(DEFAULT_FROM_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].toQuantity").value(hasItem(DEFAULT_TO_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())));
    }
    
    @Test
    @Transactional
    public void getUsertransactions() throws Exception {
        // Initialize the database
        usertransactionsRepository.saveAndFlush(usertransactions);

        // Get the usertransactions
        restUsertransactionsMockMvc.perform(get("/api/usertransactions/{id}", usertransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usertransactions.getId().intValue()))
            .andExpect(jsonPath("$.from_quantity").value(DEFAULT_FROM_QUANTITY.intValue()))
            .andExpect(jsonPath("$.toQuantity").value(DEFAULT_TO_QUANTITY.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsertransactions() throws Exception {
        // Get the usertransactions
        restUsertransactionsMockMvc.perform(get("/api/usertransactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsertransactions() throws Exception {
        // Initialize the database
        usertransactionsRepository.saveAndFlush(usertransactions);

        int databaseSizeBeforeUpdate = usertransactionsRepository.findAll().size();

        // Update the usertransactions
        Usertransactions updatedUsertransactions = usertransactionsRepository.findById(usertransactions.getId()).get();
        // Disconnect from session so that the updates on updatedUsertransactions are not directly saved in db
        em.detach(updatedUsertransactions);
        updatedUsertransactions
            .from_quantity(UPDATED_FROM_QUANTITY)
            .toQuantity(UPDATED_TO_QUANTITY)
            .action(UPDATED_ACTION);
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(updatedUsertransactions);

        restUsertransactionsMockMvc.perform(put("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the Usertransactions in the database
        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeUpdate);
        Usertransactions testUsertransactions = usertransactionsList.get(usertransactionsList.size() - 1);
        assertThat(testUsertransactions.getFrom_quantity()).isEqualTo(UPDATED_FROM_QUANTITY);
        assertThat(testUsertransactions.getToQuantity()).isEqualTo(UPDATED_TO_QUANTITY);
        assertThat(testUsertransactions.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUsertransactions() throws Exception {
        int databaseSizeBeforeUpdate = usertransactionsRepository.findAll().size();

        // Create the Usertransactions
        UsertransactionsDTO usertransactionsDTO = usertransactionsMapper.toDto(usertransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsertransactionsMockMvc.perform(put("/api/usertransactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usertransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usertransactions in the database
        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsertransactions() throws Exception {
        // Initialize the database
        usertransactionsRepository.saveAndFlush(usertransactions);

        int databaseSizeBeforeDelete = usertransactionsRepository.findAll().size();

        // Delete the usertransactions
        restUsertransactionsMockMvc.perform(delete("/api/usertransactions/{id}", usertransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usertransactions> usertransactionsList = usertransactionsRepository.findAll();
        assertThat(usertransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
