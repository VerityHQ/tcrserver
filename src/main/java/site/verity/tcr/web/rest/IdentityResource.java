package site.verity.tcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import site.verity.tcr.domain.Identity;
import site.verity.tcr.repository.IdentityRepository;
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
 * REST controller for managing Identity.
 */
@RestController
@RequestMapping("/api")
public class IdentityResource {

    private final Logger log = LoggerFactory.getLogger(IdentityResource.class);

    private static final String ENTITY_NAME = "identity";

    private final IdentityRepository identityRepository;

    public IdentityResource(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    /**
     * POST  /identities : Create a new identity.
     *
     * @param identity the identity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new identity, or with status 400 (Bad Request) if the identity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/identities")
    @Timed
    public ResponseEntity<Identity> createIdentity(@RequestBody Identity identity) throws URISyntaxException {
        log.debug("REST request to save Identity : {}", identity);
        if (identity.getId() != null) {
            throw new BadRequestAlertException("A new identity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Identity result = identityRepository.save(identity);
        return ResponseEntity.created(new URI("/api/identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /identities : Updates an existing identity.
     *
     * @param identity the identity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated identity,
     * or with status 400 (Bad Request) if the identity is not valid,
     * or with status 500 (Internal Server Error) if the identity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/identities")
    @Timed
    public ResponseEntity<Identity> updateIdentity(@RequestBody Identity identity) throws URISyntaxException {
        log.debug("REST request to update Identity : {}", identity);
        if (identity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Identity result = identityRepository.save(identity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, identity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /identities : get all the identities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of identities in body
     */
    @GetMapping("/identities")
    @Timed
    public List<Identity> getAllIdentities() {
        log.debug("REST request to get all Identities");
        return identityRepository.findAll();
    }

    /**
     * GET  /identities/:id : get the "id" identity.
     *
     * @param id the id of the identity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the identity, or with status 404 (Not Found)
     */
    @GetMapping("/identities/{id}")
    @Timed
    public ResponseEntity<Identity> getIdentity(@PathVariable Long id) {
        log.debug("REST request to get Identity : {}", id);
        Optional<Identity> identity = identityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identity);
    }

    /**
     * DELETE  /identities/:id : delete the "id" identity.
     *
     * @param id the id of the identity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/identities/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdentity(@PathVariable Long id) {
        log.debug("REST request to delete Identity : {}", id);

        identityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
