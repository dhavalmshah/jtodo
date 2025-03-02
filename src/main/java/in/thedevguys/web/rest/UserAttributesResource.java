package in.thedevguys.web.rest;

import in.thedevguys.repository.UserAttributesRepository;
import in.thedevguys.service.UserAttributesService;
import in.thedevguys.service.dto.UserAttributesDTO;
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
 * REST controller for managing {@link in.thedevguys.domain.UserAttributes}.
 */
@RestController
@RequestMapping("/api/user-attributes")
public class UserAttributesResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserAttributesResource.class);

    private static final String ENTITY_NAME = "userAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAttributesService userAttributesService;

    private final UserAttributesRepository userAttributesRepository;

    public UserAttributesResource(UserAttributesService userAttributesService, UserAttributesRepository userAttributesRepository) {
        this.userAttributesService = userAttributesService;
        this.userAttributesRepository = userAttributesRepository;
    }

    /**
     * {@code POST  /user-attributes} : Create a new userAttributes.
     *
     * @param userAttributesDTO the userAttributesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAttributesDTO, or with status {@code 400 (Bad Request)} if the userAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserAttributesDTO> createUserAttributes(@Valid @RequestBody UserAttributesDTO userAttributesDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save UserAttributes : {}", userAttributesDTO);
        if (userAttributesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userAttributesDTO = userAttributesService.save(userAttributesDTO);
        return ResponseEntity.created(new URI("/api/user-attributes/" + userAttributesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, userAttributesDTO.getId().toString()))
            .body(userAttributesDTO);
    }

    /**
     * {@code PUT  /user-attributes/:id} : Updates an existing userAttributes.
     *
     * @param id the id of the userAttributesDTO to save.
     * @param userAttributesDTO the userAttributesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributesDTO,
     * or with status {@code 400 (Bad Request)} if the userAttributesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAttributesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserAttributesDTO> updateUserAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserAttributesDTO userAttributesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UserAttributes : {}, {}", id, userAttributesDTO);
        if (userAttributesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userAttributesDTO = userAttributesService.update(userAttributesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributesDTO.getId().toString()))
            .body(userAttributesDTO);
    }

    /**
     * {@code PATCH  /user-attributes/:id} : Partial updates given fields of an existing userAttributes, field will ignore if it is null
     *
     * @param id the id of the userAttributesDTO to save.
     * @param userAttributesDTO the userAttributesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAttributesDTO,
     * or with status {@code 400 (Bad Request)} if the userAttributesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAttributesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAttributesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAttributesDTO> partialUpdateUserAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserAttributesDTO userAttributesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UserAttributes partially : {}, {}", id, userAttributesDTO);
        if (userAttributesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAttributesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAttributesDTO> result = userAttributesService.partialUpdate(userAttributesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAttributesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-attributes} : get all the userAttributes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAttributes in body.
     */
    @GetMapping("")
    public List<UserAttributesDTO> getAllUserAttributes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get all UserAttributes");
        return userAttributesService.findAll();
    }

    /**
     * {@code GET  /user-attributes/:id} : get the "id" userAttributes.
     *
     * @param id the id of the userAttributesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAttributesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAttributesDTO> getUserAttributes(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UserAttributes : {}", id);
        Optional<UserAttributesDTO> userAttributesDTO = userAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAttributesDTO);
    }

    /**
     * {@code DELETE  /user-attributes/:id} : delete the "id" userAttributes.
     *
     * @param id the id of the userAttributesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAttributes(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UserAttributes : {}", id);
        userAttributesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
