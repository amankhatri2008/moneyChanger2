package com.resortworld.moneychanger.web.rest;

import com.resortworld.moneychanger.MoneyChangerApp;
import com.resortworld.moneychanger.domain.CurrencyRate;
import com.resortworld.moneychanger.repository.CurrencyRateRepository;
import com.resortworld.moneychanger.service.CurrencyRateService;
import com.resortworld.moneychanger.service.dto.CurrencyRateDTO;
import com.resortworld.moneychanger.service.mapper.CurrencyRateMapper;
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
 * Integration tests for the {@link CurrencyRateResource} REST controller.
 */
@SpringBootTest(classes = MoneyChangerApp.class)
public class CurrencyRateResourceIT {

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BUY_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUY_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SELL_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELL_RATE = new BigDecimal(2);

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;

    @Autowired
    private CurrencyRateService currencyRateService;

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

    private MockMvc restCurrencyRateMockMvc;

    private CurrencyRate currencyRate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyRateResource currencyRateResource = new CurrencyRateResource(currencyRateService);
        this.restCurrencyRateMockMvc = MockMvcBuilders.standaloneSetup(currencyRateResource)
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
    public static CurrencyRate createEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currency(DEFAULT_CURRENCY)
            .buyRate(DEFAULT_BUY_RATE)
            .sellRate(DEFAULT_SELL_RATE);
        return currencyRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyRate createUpdatedEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currency(UPDATED_CURRENCY)
            .buyRate(UPDATED_BUY_RATE)
            .sellRate(UPDATED_SELL_RATE);
        return currencyRate;
    }

    @BeforeEach
    public void initTest() {
        currencyRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyRate() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCurrencyRate.getBuyRate()).isEqualTo(DEFAULT_BUY_RATE);
        assertThat(testCurrencyRate.getSellRate()).isEqualTo(DEFAULT_SELL_RATE);
    }

    @Test
    @Transactional
    public void createCurrencyRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate with an existing ID
        currencyRate.setId(1L);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setCurrency(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setBuyRate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setSellRate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencyRates() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].buyRate").value(hasItem(DEFAULT_BUY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].sellRate").value(hasItem(DEFAULT_SELL_RATE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", currencyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currencyRate.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.buyRate").value(DEFAULT_BUY_RATE.intValue()))
            .andExpect(jsonPath("$.sellRate").value(DEFAULT_SELL_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrencyRate() throws Exception {
        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Update the currencyRate
        CurrencyRate updatedCurrencyRate = currencyRateRepository.findById(currencyRate.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyRate are not directly saved in db
        em.detach(updatedCurrencyRate);
        updatedCurrencyRate
            .currency(UPDATED_CURRENCY)
            .buyRate(UPDATED_BUY_RATE)
            .sellRate(UPDATED_SELL_RATE);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(updatedCurrencyRate);

        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isOk());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCurrencyRate.getBuyRate()).isEqualTo(UPDATED_BUY_RATE);
        assertThat(testCurrencyRate.getSellRate()).isEqualTo(UPDATED_SELL_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyRate() throws Exception {
        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeDelete = currencyRateRepository.findAll().size();

        // Delete the currencyRate
        restCurrencyRateMockMvc.perform(delete("/api/currency-rates/{id}", currencyRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
