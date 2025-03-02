package in.thedevguys.web.rest;

import in.thedevguys.repository.BadgeRepository;
import in.thedevguys.service.BadgeService;
import in.thedevguys.service.dto.BadgeDTO;
import in.thedevguys.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link in.thedevguys.domain.Badge}.
 */
@RestController
@RequestMapping("/api/badges")
public class BadgeResource {

    private static final Logger LOG = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeService badgeService;

    private final BadgeRepository badgeRepository;

    public BadgeResource(BadgeService badgeService, BadgeRepository badgeRepository) {
        this.badgeService = badgeService;
        this.badgeRepository = badgeRepository;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param badgeDTO the badgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeDTO, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BadgeDTO> createBadge(@Valid @RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Badge : {}", badgeDTO);
        if (badgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        badgeDTO = badgeService.save(badgeDTO);
        return ResponseEntity.created(new URI("/api/badges/" + badgeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, badgeDTO.getId().toString()))
            .body(badgeDTO);
    }

    /**
     * {@code PUT  /badges/:id} : Updates an existing badge.
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BadgeDTO> updateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Badge : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        badgeDTO = badgeService.update(badgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeDTO.getId().toString()))
            .body(badgeDTO);
    }

    /**
     * {@code PATCH  /badges/:id} : Partial updates given fields of an existing badge, field will ignore if it is null
     *
     * @param id the id of the badgeDTO to save.
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the badgeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BadgeDTO> partialUpdateBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BadgeDTO badgeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Badge partially : {}, {}", id, badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, badgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!badgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BadgeDTO> result = badgeService.partialUpdate(badgeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("")
    public List<BadgeDTO> getAllBadges() {
        LOG.debug("REST request to get all Badges");
        return badgeService.findAll();
    }

    /**
     * {@code GET  /badges/:id} : get the "id" badge.
     *
     * @param id the id of the badgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BadgeDTO> getBadge(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Badge : {}", id);
        Optional<BadgeDTO> badgeDTO = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
