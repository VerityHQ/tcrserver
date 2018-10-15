package site.verity.tcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import site.verity.tcr.domain.TcrStake;
import site.verity.tcr.repository.TcrStakeRepository;
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
 * REST controller for managing TcrStake.
 */
@RestController
@RequestMapping("/api")
public class TcrStakeResource {

    private final Logger log = LoggerFactory.getLogger(TcrStakeResource.class);

    private static final String ENTITY_NAME = "tcrStake";

    private final TcrStakeRepository tcrStakeRepository;

    public TcrStakeResource(TcrStakeRepository tcrStakeRepository) {
        this.tcrStakeRepository = tcrStakeRepository;
    }

    /**
     * POST  /tcr-stakes : Create a new tcrStake.
     *
     * @param tcrStake the tcrStake to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tcrStake, or with status 400 (Bad Request) if the tcrStake has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tcr-stakes")
    @Timed
    public ResponseEntity<TcrStake> createTcrStake(@RequestBody TcrStake tcrStake) throws URISyntaxException {
        log.debug("REST request to save TcrStake : {}", tcrStake);
        if (tcrStake.getId() != null) {
            throw new BadRequestAlertException("A new tcrStake cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcrStake result = tcrStakeRepository.save(tcrStake);
        return ResponseEntity.created(new URI("/api/tcr-stakes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tcr-stakes : Updates an existing tcrStake.
     *
     * @param tcrStake the tcrStake to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tcrStake,
     * or with status 400 (Bad Request) if the tcrStake is not valid,
     * or with status 500 (Internal Server Error) if the tcrStake couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tcr-stakes")
    @Timed
    public ResponseEntity<TcrStake> updateTcrStake(@RequestBody TcrStake tcrStake) throws URISyntaxException {
        log.debug("REST request to update TcrStake : {}", tcrStake);
        if (tcrStake.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcrStake result = tcrStakeRepository.save(tcrStake);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tcrStake.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tcr-stakes : get all the tcrStakes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tcrStakes in body
     */
    @GetMapping("/tcr-stakes")
    @Timed
    public List<TcrStake> getAllTcrStakes() {
        log.debug("REST request to get all TcrStakes");
        return tcrStakeRepository.findAll();
    }

    /**
     * GET  /tcr-stakes/:id : get the "id" tcrStake.
     *
     * @param id the id of the tcrStake to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tcrStake, or with status 404 (Not Found)
     */
    @GetMapping("/tcr-stakes/{id}")
    @Timed
    public ResponseEntity<TcrStake> getTcrStake(@PathVariable Long id) {
        log.debug("REST request to get TcrStake : {}", id);
        Optional<TcrStake> tcrStake = tcrStakeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tcrStake);
    }

    /**
     * DELETE  /tcr-stakes/:id : delete the "id" tcrStake.
     *
     * @param id the id of the tcrStake to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tcr-stakes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTcrStake(@PathVariable Long id) {
        log.debug("REST request to delete TcrStake : {}", id);

        tcrStakeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
