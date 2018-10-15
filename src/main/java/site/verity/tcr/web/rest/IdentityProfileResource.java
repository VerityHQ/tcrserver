package site.verity.tcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import site.verity.tcr.domain.IdentityProfile;
import site.verity.tcr.repository.IdentityProfileRepository;
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
 * REST controller for managing IdentityProfile.
 */
@RestController
@RequestMapping("/api")
public class IdentityProfileResource {

    private final Logger log = LoggerFactory.getLogger(IdentityProfileResource.class);

    private static final String ENTITY_NAME = "identityProfile";

    private final IdentityProfileRepository identityProfileRepository;

    public IdentityProfileResource(IdentityProfileRepository identityProfileRepository) {
        this.identityProfileRepository = identityProfileRepository;
    }

    /**
     * POST  /identity-profiles : Create a new identityProfile.
     *
     * @param identityProfile the identityProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new identityProfile, or with status 400 (Bad Request) if the identityProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/identity-profiles")
    @Timed
    public ResponseEntity<IdentityProfile> createIdentityProfile(@RequestBody IdentityProfile identityProfile) throws URISyntaxException {
        log.debug("REST request to save IdentityProfile : {}", identityProfile);
        if (identityProfile.getId() != null) {
            throw new BadRequestAlertException("A new identityProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdentityProfile result = identityProfileRepository.save(identityProfile);
        return ResponseEntity.created(new URI("/api/identity-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /identity-profiles : Updates an existing identityProfile.
     *
     * @param identityProfile the identityProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated identityProfile,
     * or with status 400 (Bad Request) if the identityProfile is not valid,
     * or with status 500 (Internal Server Error) if the identityProfile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/identity-profiles")
    @Timed
    public ResponseEntity<IdentityProfile> updateIdentityProfile(@RequestBody IdentityProfile identityProfile) throws URISyntaxException {
        log.debug("REST request to update IdentityProfile : {}", identityProfile);
        if (identityProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdentityProfile result = identityProfileRepository.save(identityProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, identityProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /identity-profiles : get all the identityProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of identityProfiles in body
     */
    @GetMapping("/identity-profiles")
    @Timed
    public List<IdentityProfile> getAllIdentityProfiles() {
        log.debug("REST request to get all IdentityProfiles");
        return identityProfileRepository.findAll();
    }

    /**
     * GET  /identity-profiles/:id : get the "id" identityProfile.
     *
     * @param id the id of the identityProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the identityProfile, or with status 404 (Not Found)
     */
    @GetMapping("/identity-profiles/{id}")
    @Timed
    public ResponseEntity<IdentityProfile> getIdentityProfile(@PathVariable Long id) {
        log.debug("REST request to get IdentityProfile : {}", id);
        Optional<IdentityProfile> identityProfile = identityProfileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identityProfile);
    }

    /**
     * DELETE  /identity-profiles/:id : delete the "id" identityProfile.
     *
     * @param id the id of the identityProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/identity-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdentityProfile(@PathVariable Long id) {
        log.debug("REST request to delete IdentityProfile : {}", id);

        identityProfileRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
