package site.verity.tcr.web.rest;

import site.verity.tcr.TcrserverApp;

import site.verity.tcr.domain.IdentityProfile;
import site.verity.tcr.repository.IdentityProfileRepository;
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
 * Test class for the IdentityProfileResource REST controller.
 *
 * @see IdentityProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcrserverApp.class)
public class IdentityProfileResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REDDIT_URL = "AAAAAAAAAA";
    private static final String UPDATED_REDDIT_URL = "BBBBBBBBBB";

    @Autowired
    private IdentityProfileRepository identityProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIdentityProfileMockMvc;

    private IdentityProfile identityProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdentityProfileResource identityProfileResource = new IdentityProfileResource(identityProfileRepository);
        this.restIdentityProfileMockMvc = MockMvcBuilders.standaloneSetup(identityProfileResource)
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
    public static IdentityProfile createEntity(EntityManager em) {
        IdentityProfile identityProfile = new IdentityProfile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .redditUrl(DEFAULT_REDDIT_URL);
        return identityProfile;
    }

    @Before
    public void initTest() {
        identityProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentityProfile() throws Exception {
        int databaseSizeBeforeCreate = identityProfileRepository.findAll().size();

        // Create the IdentityProfile
        restIdentityProfileMockMvc.perform(post("/api/identity-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityProfile)))
            .andExpect(status().isCreated());

        // Validate the IdentityProfile in the database
        List<IdentityProfile> identityProfileList = identityProfileRepository.findAll();
        assertThat(identityProfileList).hasSize(databaseSizeBeforeCreate + 1);
        IdentityProfile testIdentityProfile = identityProfileList.get(identityProfileList.size() - 1);
        assertThat(testIdentityProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIdentityProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIdentityProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testIdentityProfile.getRedditUrl()).isEqualTo(DEFAULT_REDDIT_URL);
    }

    @Test
    @Transactional
    public void createIdentityProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identityProfileRepository.findAll().size();

        // Create the IdentityProfile with an existing ID
        identityProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityProfileMockMvc.perform(post("/api/identity-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityProfile)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityProfile in the database
        List<IdentityProfile> identityProfileList = identityProfileRepository.findAll();
        assertThat(identityProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIdentityProfiles() throws Exception {
        // Initialize the database
        identityProfileRepository.saveAndFlush(identityProfile);

        // Get all the identityProfileList
        restIdentityProfileMockMvc.perform(get("/api/identity-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identityProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].redditUrl").value(hasItem(DEFAULT_REDDIT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getIdentityProfile() throws Exception {
        // Initialize the database
        identityProfileRepository.saveAndFlush(identityProfile);

        // Get the identityProfile
        restIdentityProfileMockMvc.perform(get("/api/identity-profiles/{id}", identityProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(identityProfile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.redditUrl").value(DEFAULT_REDDIT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIdentityProfile() throws Exception {
        // Get the identityProfile
        restIdentityProfileMockMvc.perform(get("/api/identity-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentityProfile() throws Exception {
        // Initialize the database
        identityProfileRepository.saveAndFlush(identityProfile);

        int databaseSizeBeforeUpdate = identityProfileRepository.findAll().size();

        // Update the identityProfile
        IdentityProfile updatedIdentityProfile = identityProfileRepository.findById(identityProfile.getId()).get();
        // Disconnect from session so that the updates on updatedIdentityProfile are not directly saved in db
        em.detach(updatedIdentityProfile);
        updatedIdentityProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .redditUrl(UPDATED_REDDIT_URL);

        restIdentityProfileMockMvc.perform(put("/api/identity-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdentityProfile)))
            .andExpect(status().isOk());

        // Validate the IdentityProfile in the database
        List<IdentityProfile> identityProfileList = identityProfileRepository.findAll();
        assertThat(identityProfileList).hasSize(databaseSizeBeforeUpdate);
        IdentityProfile testIdentityProfile = identityProfileList.get(identityProfileList.size() - 1);
        assertThat(testIdentityProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIdentityProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIdentityProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testIdentityProfile.getRedditUrl()).isEqualTo(UPDATED_REDDIT_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentityProfile() throws Exception {
        int databaseSizeBeforeUpdate = identityProfileRepository.findAll().size();

        // Create the IdentityProfile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityProfileMockMvc.perform(put("/api/identity-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityProfile)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityProfile in the database
        List<IdentityProfile> identityProfileList = identityProfileRepository.findAll();
        assertThat(identityProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentityProfile() throws Exception {
        // Initialize the database
        identityProfileRepository.saveAndFlush(identityProfile);

        int databaseSizeBeforeDelete = identityProfileRepository.findAll().size();

        // Get the identityProfile
        restIdentityProfileMockMvc.perform(delete("/api/identity-profiles/{id}", identityProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IdentityProfile> identityProfileList = identityProfileRepository.findAll();
        assertThat(identityProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityProfile.class);
        IdentityProfile identityProfile1 = new IdentityProfile();
        identityProfile1.setId(1L);
        IdentityProfile identityProfile2 = new IdentityProfile();
        identityProfile2.setId(identityProfile1.getId());
        assertThat(identityProfile1).isEqualTo(identityProfile2);
        identityProfile2.setId(2L);
        assertThat(identityProfile1).isNotEqualTo(identityProfile2);
        identityProfile1.setId(null);
        assertThat(identityProfile1).isNotEqualTo(identityProfile2);
    }
}
