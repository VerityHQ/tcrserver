package site.verity.tcr.web.rest;

import com.codahale.metrics.annotation.Timed;
import site.verity.tcr.domain.Tcr;
import site.verity.tcr.repository.TcrRepository;
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
 * REST controller for managing Tcr.
 */
@RestController
@RequestMapping("/api")
public class TcrResource {

    private final Logger log = LoggerFactory.getLogger(TcrResource.class);

    private static final String ENTITY_NAME = "tcr";

    private final TcrRepository tcrRepository;

    public TcrResource(TcrRepository tcrRepository) {
        this.tcrRepository = tcrRepository;
    }

    /**
     * POST  /tcrs : Create a new tcr.
     *
     * @param tcr the tcr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tcr, or with status 400 (Bad Request) if the tcr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tcrs")
    @Timed
    public ResponseEntity<Tcr> createTcr(@RequestBody Tcr tcr) throws URISyntaxException {
        log.debug("REST request to save Tcr : {}", tcr);
        if (tcr.getId() != null) {
            throw new BadRequestAlertException("A new tcr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tcr result = tcrRepository.save(tcr);
        return ResponseEntity.created(new URI("/api/tcrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tcrs : Updates an existing tcr.
     *
     * @param tcr the tcr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tcr,
     * or with status 400 (Bad Request) if the tcr is not valid,
     * or with status 500 (Internal Server Error) if the tcr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tcrs")
    @Timed
    public ResponseEntity<Tcr> updateTcr(@RequestBody Tcr tcr) throws URISyntaxException {
        log.debug("REST request to update Tcr : {}", tcr);
        if (tcr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tcr result = tcrRepository.save(tcr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tcr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tcrs : get all the tcrs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tcrs in body
     */
    @GetMapping("/tcrs")
    @Timed
    public List<Tcr> getAllTcrs() {
        log.debug("REST request to get all Tcrs");
        return tcrRepository.findAll();
    }

    /**
     * GET  /tcrs/:id : get the "id" tcr.
     *
     * @param id the id of the tcr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tcr, or with status 404 (Not Found)
     */
    @GetMapping("/tcrs/{id}")
    @Timed
    public ResponseEntity<Tcr> getTcr(@PathVariable Long id) {
        log.debug("REST request to get Tcr : {}", id);
        Optional<Tcr> tcr = tcrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tcr);
    }

    /**
     * DELETE  /tcrs/:id : delete the "id" tcr.
     *
     * @param id the id of the tcr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tcrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTcr(@PathVariable Long id) {
        log.debug("REST request to delete Tcr : {}", id);

        tcrRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
