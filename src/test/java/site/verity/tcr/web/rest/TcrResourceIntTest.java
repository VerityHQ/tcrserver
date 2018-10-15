package site.verity.tcr.web.rest;

import site.verity.tcr.TcrserverApp;

import site.verity.tcr.domain.Tcr;
import site.verity.tcr.repository.TcrRepository;
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
 * Test class for the TcrResource REST controller.
 *
 * @see TcrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcrserverApp.class)
public class TcrResourceIntTest {

    private static final String DEFAULT_TCR_HASH = "AAAAAAAAAA";
    private static final String UPDATED_TCR_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_TCR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TCR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_BONDING_CURVE = 1L;
    private static final Long UPDATED_BONDING_CURVE = 2L;

    @Autowired
    private TcrRepository tcrRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTcrMockMvc;

    private Tcr tcr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TcrResource tcrResource = new TcrResource(tcrRepository);
        this.restTcrMockMvc = MockMvcBuilders.standaloneSetup(tcrResource)
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
    public static Tcr createEntity(EntityManager em) {
        Tcr tcr = new Tcr()
            .tcrHash(DEFAULT_TCR_HASH)
            .tcrName(DEFAULT_TCR_NAME)
            .content(DEFAULT_CONTENT)
            .bondingCurve(DEFAULT_BONDING_CURVE);
        return tcr;
    }

    @Before
    public void initTest() {
        tcr = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcr() throws Exception {
        int databaseSizeBeforeCreate = tcrRepository.findAll().size();

        // Create the Tcr
        restTcrMockMvc.perform(post("/api/tcrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcr)))
            .andExpect(status().isCreated());

        // Validate the Tcr in the database
        List<Tcr> tcrList = tcrRepository.findAll();
        assertThat(tcrList).hasSize(databaseSizeBeforeCreate + 1);
        Tcr testTcr = tcrList.get(tcrList.size() - 1);
        assertThat(testTcr.getTcrHash()).isEqualTo(DEFAULT_TCR_HASH);
        assertThat(testTcr.getTcrName()).isEqualTo(DEFAULT_TCR_NAME);
        assertThat(testTcr.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTcr.getBondingCurve()).isEqualTo(DEFAULT_BONDING_CURVE);
    }

    @Test
    @Transactional
    public void createTcrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcrRepository.findAll().size();

        // Create the Tcr with an existing ID
        tcr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcrMockMvc.perform(post("/api/tcrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcr)))
            .andExpect(status().isBadRequest());

        // Validate the Tcr in the database
        List<Tcr> tcrList = tcrRepository.findAll();
        assertThat(tcrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTcrs() throws Exception {
        // Initialize the database
        tcrRepository.saveAndFlush(tcr);

        // Get all the tcrList
        restTcrMockMvc.perform(get("/api/tcrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcr.getId().intValue())))
            .andExpect(jsonPath("$.[*].tcrHash").value(hasItem(DEFAULT_TCR_HASH.toString())))
            .andExpect(jsonPath("$.[*].tcrName").value(hasItem(DEFAULT_TCR_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].bondingCurve").value(hasItem(DEFAULT_BONDING_CURVE.intValue())));
    }
    
    @Test
    @Transactional
    public void getTcr() throws Exception {
        // Initialize the database
        tcrRepository.saveAndFlush(tcr);

        // Get the tcr
        restTcrMockMvc.perform(get("/api/tcrs/{id}", tcr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tcr.getId().intValue()))
            .andExpect(jsonPath("$.tcrHash").value(DEFAULT_TCR_HASH.toString()))
            .andExpect(jsonPath("$.tcrName").value(DEFAULT_TCR_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.bondingCurve").value(DEFAULT_BONDING_CURVE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTcr() throws Exception {
        // Get the tcr
        restTcrMockMvc.perform(get("/api/tcrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcr() throws Exception {
        // Initialize the database
        tcrRepository.saveAndFlush(tcr);

        int databaseSizeBeforeUpdate = tcrRepository.findAll().size();

        // Update the tcr
        Tcr updatedTcr = tcrRepository.findById(tcr.getId()).get();
        // Disconnect from session so that the updates on updatedTcr are not directly saved in db
        em.detach(updatedTcr);
        updatedTcr
            .tcrHash(UPDATED_TCR_HASH)
            .tcrName(UPDATED_TCR_NAME)
            .content(UPDATED_CONTENT)
            .bondingCurve(UPDATED_BONDING_CURVE);

        restTcrMockMvc.perform(put("/api/tcrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTcr)))
            .andExpect(status().isOk());

        // Validate the Tcr in the database
        List<Tcr> tcrList = tcrRepository.findAll();
        assertThat(tcrList).hasSize(databaseSizeBeforeUpdate);
        Tcr testTcr = tcrList.get(tcrList.size() - 1);
        assertThat(testTcr.getTcrHash()).isEqualTo(UPDATED_TCR_HASH);
        assertThat(testTcr.getTcrName()).isEqualTo(UPDATED_TCR_NAME);
        assertThat(testTcr.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTcr.getBondingCurve()).isEqualTo(UPDATED_BONDING_CURVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTcr() throws Exception {
        int databaseSizeBeforeUpdate = tcrRepository.findAll().size();

        // Create the Tcr

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcrMockMvc.perform(put("/api/tcrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tcr)))
            .andExpect(status().isBadRequest());

        // Validate the Tcr in the database
        List<Tcr> tcrList = tcrRepository.findAll();
        assertThat(tcrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcr() throws Exception {
        // Initialize the database
        tcrRepository.saveAndFlush(tcr);

        int databaseSizeBeforeDelete = tcrRepository.findAll().size();

        // Get the tcr
        restTcrMockMvc.perform(delete("/api/tcrs/{id}", tcr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tcr> tcrList = tcrRepository.findAll();
        assertThat(tcrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tcr.class);
        Tcr tcr1 = new Tcr();
        tcr1.setId(1L);
        Tcr tcr2 = new Tcr();
        tcr2.setId(tcr1.getId());
        assertThat(tcr1).isEqualTo(tcr2);
        tcr2.setId(2L);
        assertThat(tcr1).isNotEqualTo(tcr2);
        tcr1.setId(null);
        assertThat(tcr1).isNotEqualTo(tcr2);
    }
}
