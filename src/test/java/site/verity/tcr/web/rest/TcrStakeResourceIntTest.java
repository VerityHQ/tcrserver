package site.verity.tcr.web.rest;

import site.verity.tcr.TcrserverApp;

import site.verity.tcr.domain.TcrStake;
import site.verity.tcr.repository.TcrStakeRepository;
import site.verity.tcr.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static site.verity.tcr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TcrStakeResource REST controller.
 *
 * @see TcrStakeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcrserverApp.class)
public class TcrStakeResourceIntTest {

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Long DEFAULT_SHARES_ISSUED = 1L;
    private static final Long UPDATED_SHARES_ISSUED = 2L;

    private static final String DEFAULT_OWNER_IDENTITY = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_IDENTITY = "BBBBBBBBBB";

    @Autowired
    private TcrStakeRepository tcrStakeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTcrStakeMockMvc;

    private TcrStake tcrStake;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TcrStakeResource tcrStakeResource = new TcrStakeResource(tcrStakeRepository);
        this.restTcrStakeMockMvc = MockMvcBuilders.standaloneSetup(tcrStakeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcrStake createEntity(EntityManager em) {
        TcrStake tcrStake = new TcrStake()
            .amount(DEFAULT_AMOUNT)
            .token(DEFAULT_TOKEN)
            .sharesIssued(DEFAULT_SHARES_ISSUED)
            .ownerIdentity(DEFAULT_OWNER_IDENTITY);
        return tcrStake;
    }

    @Before
    public void initTest() {
        tcrStake = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcrStake() throws Exception {
        int databaseSizeBeforeCreate = tcrStakeRepository.findAll().size();

        // Create the TcrStake
        restTcrStakeMockMvc.perform(post("/api/tcr-stakes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcrStake)))
            .andExpect(status().isCreated());

        // Validate the TcrStake in the database
        List<TcrStake> tcrStakeList = tcrStakeRepository.findAll();
        assertThat(tcrStakeList).hasSize(databaseSizeBeforeCreate + 1);
        TcrStake testTcrStake = tcrStakeList.get(tcrStakeList.size() - 1);
        assertThat(testTcrStake.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTcrStake.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTcrStake.getSharesIssued()).isEqualTo(DEFAULT_SHARES_ISSUED);
        assertThat(testTcrStake.getOwnerIdentity()).isEqualTo(DEFAULT_OWNER_IDENTITY);
    }

    @Test
    @Transactional
    public void createTcrStakeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcrStakeRepository.findAll().size();

        // Create the TcrStake with an existing ID
        tcrStake.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcrStakeMockMvc.perform(post("/api/tcr-stakes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcrStake)))
            .andExpect(status().isBadRequest());

        // Validate the TcrStake in the database
        List<TcrStake> tcrStakeList = tcrStakeRepository.findAll();
        assertThat(tcrStakeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTcrStakes() throws Exception {
        // Initialize the database
        tcrStakeRepository.saveAndFlush(tcrStake);

        // Get all the tcrStakeList
        restTcrStakeMockMvc.perform(get("/api/tcr-stakes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcrStake.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].sharesIssued").value(hasItem(DEFAULT_SHARES_ISSUED.intValue())))
            .andExpect(jsonPath("$.[*].ownerIdentity").value(hasItem(DEFAULT_OWNER_IDENTITY.toString())));
    }
    
    @Test
    @Transactional
    public void getTcrStake() throws Exception {
        // Initialize the database
        tcrStakeRepository.saveAndFlush(tcrStake);

        // Get the tcrStake
        restTcrStakeMockMvc.perform(get("/api/tcr-stakes/{id}", tcrStake.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tcrStake.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.sharesIssued").value(DEFAULT_SHARES_ISSUED.intValue()))
            .andExpect(jsonPath("$.ownerIdentity").value(DEFAULT_OWNER_IDENTITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTcrStake() throws Exception {
        // Get the tcrStake
        restTcrStakeMockMvc.perform(get("/api/tcr-stakes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcrStake() throws Exception {
        // Initialize the database
        tcrStakeRepository.saveAndFlush(tcrStake);

        int databaseSizeBeforeUpdate = tcrStakeRepository.findAll().size();

        // Update the tcrStake
        TcrStake updatedTcrStake = tcrStakeRepository.findById(tcrStake.getId()).get();
        // Disconnect from session so that the updates on updatedTcrStake are not directly saved in db
        em.detach(updatedTcrStake);
        updatedTcrStake
            .amount(UPDATED_AMOUNT)
            .token(UPDATED_TOKEN)
            .sharesIssued(UPDATED_SHARES_ISSUED)
            .ownerIdentity(UPDATED_OWNER_IDENTITY);

        restTcrStakeMockMvc.perform(put("/api/tcr-stakes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTcrStake)))
            .andExpect(status().isOk());

        // Validate the TcrStake in the database
        List<TcrStake> tcrStakeList = tcrStakeRepository.findAll();
        assertThat(tcrStakeList).hasSize(databaseSizeBeforeUpdate);
        TcrStake testTcrStake = tcrStakeList.get(tcrStakeList.size() - 1);
        assertThat(testTcrStake.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTcrStake.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTcrStake.getSharesIssued()).isEqualTo(UPDATED_SHARES_ISSUED);
        assertThat(testTcrStake.getOwnerIdentity()).isEqualTo(UPDATED_OWNER_IDENTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingTcrStake() throws Exception {
        int databaseSizeBeforeUpdate = tcrStakeRepository.findAll().size();

        // Create the TcrStake

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcrStakeMockMvc.perform(put("/api/tcr-stakes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcrStake)))
            .andExpect(status().isBadRequest());

        // Validate the TcrStake in the database
        List<TcrStake> tcrStakeList = tcrStakeRepository.findAll();
        assertThat(tcrStakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcrStake() throws Exception {
        // Initialize the database
        tcrStakeRepository.saveAndFlush(tcrStake);

        int databaseSizeBeforeDelete = tcrStakeRepository.findAll().size();

        // Get the tcrStake
        restTcrStakeMockMvc.perform(delete("/api/tcr-stakes/{id}", tcrStake.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TcrStake> tcrStakeList = tcrStakeRepository.findAll();
        assertThat(tcrStakeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcrStake.class);
        TcrStake tcrStake1 = new TcrStake();
        tcrStake1.setId(1L);
        TcrStake tcrStake2 = new TcrStake();
        tcrStake2.setId(tcrStake1.getId());
        assertThat(tcrStake1).isEqualTo(tcrStake2);
        tcrStake2.setId(2L);
        assertThat(tcrStake1).isNotEqualTo(tcrStake2);
        tcrStake1.setId(null);
        assertThat(tcrStake1).isNotEqualTo(tcrStake2);
    }
}
