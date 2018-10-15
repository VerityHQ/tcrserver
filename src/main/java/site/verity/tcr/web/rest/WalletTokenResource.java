package site.verity.tcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import site.verity.tcr.domain.WalletToken;
import site.verity.tcr.repository.WalletTokenRepository;
import site.verity.tcr.web.rest.errors.BadRequestAlertException;
import site.verity.tcr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WalletToken.
 */
@RestController
@RequestMapping("/api")
public class WalletTokenResource {

    private final Logger log = LoggerFactory.getLogger(WalletTokenResource.class);

    private static final String ENTITY_NAME = "walletToken";

    private final WalletTokenRepository walletTokenRepository;

    public WalletTokenResource(WalletTokenRepository walletTokenRepository) {
        this.walletTokenRepository = walletTokenRepository;
    }

    /**
     * POST  /wallet-tokens : Create a new walletToken.
     *
     * @param walletToken the walletToken to create
     * @return the ResponseEntity with status 201 (Created) and with body the new walletToken, or with status 400 (Bad Request) if the walletToken has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wallet-tokens")
    @Timed
    public ResponseEntity<WalletToken> createWalletToken(@RequestBody WalletToken walletToken) throws URISyntaxException {
        log.debug("REST request to save WalletToken : {}", walletToken);
        if (walletToken.getId() != null) {
            throw new BadRequestAlertException("A new walletToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletToken result = walletTokenRepository.save(walletToken);
        return ResponseEntity.created(new URI("/api/wallet-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wallet-tokens : Updates an existing walletToken.
     *
     * @param walletToken the walletToken to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated walletToken,
     * or with status 400 (Bad Request) if the walletToken is not valid,
     * or with status 500 (Internal Server Error) if the walletToken couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wallet-tokens")
    @Timed
    public ResponseEntity<WalletToken> updateWalletToken(@RequestBody WalletToken walletToken) throws URISyntaxException {
        log.debug("REST request to update WalletToken : {}", walletToken);
        if (walletToken.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalletToken result = walletTokenRepository.save(walletToken);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, walletToken.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wallet-tokens : get all the walletTokens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of walletTokens in body
     */
    @GetMapping("/wallet-tokens")
    @Timed
    public List<WalletToken> getAllWalletTokens() {
        log.debug("REST request to get all WalletTokens");
        return walletTokenRepository.findAll();
    }

    /**
     * GET  /wallet-tokens/:id : get the "id" walletToken.
     *
     * @param id the id of the walletToken to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the walletToken, or with status 404 (Not Found)
     */
    @GetMapping("/wallet-tokens/{id}")
    @Timed
    public ResponseEntity<WalletToken> getWalletToken(@PathVariable Long id) {
        log.debug("REST request to get WalletToken : {}", id);
        Optional<WalletToken> walletToken = walletTokenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walletToken);
    }

    /**
     * DELETE  /wallet-tokens/:id : delete the "id" walletToken.
     *
     * @param id the id of the walletToken to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet-tokens/{id}")
    @Timed
    public ResponseEntity<Void> deleteWalletToken(@PathVariable Long id) {
        log.debug("REST request to delete WalletToken : {}", id);

        walletTokenRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
