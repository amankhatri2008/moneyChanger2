package com.resortworld.moneychanger.web.rest;

import com.resortworld.moneychanger.MoneyChangerApp;
import com.resortworld.moneychanger.domain.UserBalance;
import com.resortworld.moneychanger.domain.User;
import com.resortworld.moneychanger.repository.UserBalanceRepository;
import com.resortworld.moneychanger.service.UserBalanceService;
import com.resortworld.moneychanger.service.dto.UserBalanceDTO;
import com.resortworld.moneychanger.service.mapper.UserBalanceMapper;
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

/**
 * Integration tests for the {@link UserBalanceResource} REST controller.
 */
@SpringBootTest(classes = MoneyChangerApp.class)
public class UserBalanceResourceIT {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserBalanceService userBalanceService;

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

    private MockMvc restUserBalanceMockMvc;

    private UserBalance userBalance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserBalanceResource userBalanceResource = new UserBalanceResource(userBalanceService);
        this.restUserBalanceMockMvc = MockMvcBuilders.standaloneSetup(userBalanceResource)
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
    public static UserBalance createEntity(EntityManager em) {
        UserBalance userBalance = new UserBalance()
            .balance(DEFAULT_BALANCE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userBalance.setName(user);
        return userBalance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBalance createUpdatedEntity(EntityManager em) {
        UserBalance userBalance = new UserBalance()
            .balance(UPDATED_BALANCE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userBalance.setName(user);
        return userBalance;
    }

    @BeforeEach
    public void initTest() {
        userBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBalance() throws Exception {
        int databaseSizeBeforeCreate = userBalanceRepository.findAll().size();

        // Create the UserBalance
        UserBalanceDTO userBalanceDTO = userBalanceMapper.toDto(userBalance);
        restUserBalanceMockMvc.perform(post("/api/userBalances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserBalance in the database
        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        UserBalance testUserBalance = userBalanceList.get(userBalanceList.size() - 1);
        assertThat(testUserBalance.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createUserBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBalanceRepository.findAll().size();

        // Create the UserBalance with an existing ID
        userBalance.setId(1L);
        UserBalanceDTO userBalanceDTO = userBalanceMapper.toDto(userBalance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBalanceMockMvc.perform(post("/api/userBalances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBalance in the database
        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBalanceRepository.findAll().size();
        // set the field null
        userBalance.setBalance(null);

        // Create the UserBalance, which fails.
        UserBalanceDTO userBalanceDTO = userBalanceMapper.toDto(userBalance);

        restUserBalanceMockMvc.perform(post("/api/userBalances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBalanceDTO)))
            .andExpect(status().isBadRequest());

        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserBalances() throws Exception {
        // Initialize the database
        userBalanceRepository.saveAndFlush(userBalance);

        // Get all the userBalanceList
        restUserBalanceMockMvc.perform(get("/api/userBalances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserBalance() throws Exception {
        // Initialize the database
        userBalanceRepository.saveAndFlush(userBalance);

        // Get the userBalance
        restUserBalanceMockMvc.perform(get("/api/userBalances/{id}", userBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userBalance.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserBalance() throws Exception {
        // Get the userBalance
        restUserBalanceMockMvc.perform(get("/api/userBalances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBalance() throws Exception {
        // Initialize the database
        userBalanceRepository.saveAndFlush(userBalance);

        int databaseSizeBeforeUpdate = userBalanceRepository.findAll().size();

        // Update the userBalance
        UserBalance updatedUserBalance = userBalanceRepository.findById(userBalance.getId()).get();
        // Disconnect from session so that the updates on updatedUserBalance are not directly saved in db
        em.detach(updatedUserBalance);
        updatedUserBalance
            .balance(UPDATED_BALANCE);
        UserBalanceDTO userBalanceDTO = userBalanceMapper.toDto(updatedUserBalance);

        restUserBalanceMockMvc.perform(put("/api/userBalances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBalanceDTO)))
            .andExpect(status().isOk());

        // Validate the UserBalance in the database
        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeUpdate);
        UserBalance testUserBalance = userBalanceList.get(userBalanceList.size() - 1);
        assertThat(testUserBalance.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBalance() throws Exception {
        int databaseSizeBeforeUpdate = userBalanceRepository.findAll().size();

        // Create the UserBalance
        UserBalanceDTO userBalanceDTO = userBalanceMapper.toDto(userBalance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBalanceMockMvc.perform(put("/api/userBalances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBalance in the database
        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserBalance() throws Exception {
        // Initialize the database
        userBalanceRepository.saveAndFlush(userBalance);

        int databaseSizeBeforeDelete = userBalanceRepository.findAll().size();

        // Delete the userBalance
        restUserBalanceMockMvc.perform(delete("/api/userBalances/{id}", userBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserBalance> userBalanceList = userBalanceRepository.findAll();
        assertThat(userBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
