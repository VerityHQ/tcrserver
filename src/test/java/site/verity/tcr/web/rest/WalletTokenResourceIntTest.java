package site.verity.tcr.web.rest;

import site.verity.tcr.TcrserverApp;

import site.verity.tcr.domain.WalletToken;
import site.verity.tcr.repository.WalletTokenRepository;
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
 * Test class for the WalletTokenResource REST controller.
 *
 * @see WalletTokenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TcrserverApp.class)
public class WalletTokenResourceIntTest {

    private static final String DEFAULT_IDENTITY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    @Autowired
    private WalletTokenRepository walletTokenRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWalletTokenMockMvc;

    private WalletToken walletToken;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WalletTokenResource walletTokenResource = new WalletTokenResource(walletTokenRepository);
        this.restWalletTokenMockMvc = MockMvcBuilders.standaloneSetup(walletTokenResource)
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
    public static WalletToken createEntity(EntityManager em) {
        WalletToken walletToken = new WalletToken()
            .identityAddress(DEFAULT_IDENTITY_ADDRESS)
            .token(DEFAULT_TOKEN)
            .amount(DEFAULT_AMOUNT);
        return walletToken;
    }

    @Before
    public void initTest() {
        walletToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalletToken() throws Exception {
        int databaseSizeBeforeCreate = walletTokenRepository.findAll().size();

        // Create the WalletToken
        restWalletTokenMockMvc.perform(post("/api/wallet-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletToken)))
            .andExpect(status().isCreated());

        // Validate the WalletToken in the database
        List<WalletToken> walletTokenList = walletTokenRepository.findAll();
        assertThat(walletTokenList).hasSize(databaseSizeBeforeCreate + 1);
        WalletToken testWalletToken = walletTokenList.get(walletTokenList.size() - 1);
        assertThat(testWalletToken.getIdentityAddress()).isEqualTo(DEFAULT_IDENTITY_ADDRESS);
        assertThat(testWalletToken.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testWalletToken.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createWalletTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletTokenRepository.findAll().size();

        // Create the WalletToken with an existing ID
        walletToken.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletTokenMockMvc.perform(post("/api/wallet-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletToken)))
            .andExpect(status().isBadRequest());

        // Validate the WalletToken in the database
        List<WalletToken> walletTokenList = walletTokenRepository.findAll();
        assertThat(walletTokenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWalletTokens() throws Exception {
        // Initialize the database
        walletTokenRepository.saveAndFlush(walletToken);

        // Get all the walletTokenList
        restWalletTokenMockMvc.perform(get("/api/wallet-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].identityAddress").value(hasItem(DEFAULT_IDENTITY_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getWalletToken() throws Exception {
        // Initialize the database
        walletTokenRepository.saveAndFlush(walletToken);

        // Get the walletToken
        restWalletTokenMockMvc.perform(get("/api/wallet-tokens/{id}", walletToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(walletToken.getId().intValue()))
            .andExpect(jsonPath("$.identityAddress").value(DEFAULT_IDENTITY_ADDRESS.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWalletToken() throws Exception {
        // Get the walletToken
        restWalletTokenMockMvc.perform(get("/api/wallet-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalletToken() throws Exception {
        // Initialize the database
        walletTokenRepository.saveAndFlush(walletToken);

        int databaseSizeBeforeUpdate = walletTokenRepository.findAll().size();

        // Update the walletToken
        WalletToken updatedWalletToken = walletTokenRepository.findById(walletToken.getId()).get();
        // Disconnect from session so that the updates on updatedWalletToken are not directly saved in db
        em.detach(updatedWalletToken);
        updatedWalletToken
            .identityAddress(UPDATED_IDENTITY_ADDRESS)
            .token(UPDATED_TOKEN)
            .amount(UPDATED_AMOUNT);

        restWalletTokenMockMvc.perform(put("/api/wallet-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalletToken)))
            .andExpect(status().isOk());

        // Validate the WalletToken in the database
        List<WalletToken> walletTokenList = walletTokenRepository.findAll();
        assertThat(walletTokenList).hasSize(databaseSizeBeforeUpdate);
        WalletToken testWalletToken = walletTokenList.get(walletTokenList.size() - 1);
        assertThat(testWalletToken.getIdentityAddress()).isEqualTo(UPDATED_IDENTITY_ADDRESS);
        assertThat(testWalletToken.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testWalletToken.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingWalletToken() throws Exception {
        int databaseSizeBeforeUpdate = walletTokenRepository.findAll().size();

        // Create the WalletToken

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTokenMockMvc.perform(put("/api/wallet-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletToken)))
            .andExpect(status().isBadRequest());

        // Validate the WalletToken in the database
        List<WalletToken> walletTokenList = walletTokenRepository.findAll();
        assertThat(walletTokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWalletToken() throws Exception {
        // Initialize the database
        walletTokenRepository.saveAndFlush(walletToken);

        int databaseSizeBeforeDelete = walletTokenRepository.findAll().size();

        // Get the walletToken
        restWalletTokenMockMvc.perform(delete("/api/wallet-tokens/{id}", walletToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WalletToken> walletTokenList = walletTokenRepository.findAll();
        assertThat(walletTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletToken.class);
        WalletToken walletToken1 = new WalletToken();
        walletToken1.setId(1L);
        WalletToken walletToken2 = new WalletToken();
        walletToken2.setId(walletToken1.getId());
        assertThat(walletToken1).isEqualTo(walletToken2);
        walletToken2.setId(2L);
        assertThat(walletToken1).isNotEqualTo(walletToken2);
        walletToken1.setId(null);
        assertThat(walletToken1).isNotEqualTo(walletToken2);
    }
}
