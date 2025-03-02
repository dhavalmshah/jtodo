package in.thedevguys.web.rest;

import static in.thedevguys.domain.BadgeAsserts.*;
import static in.thedevguys.web.rest.TestUtil.createUpdateProxyForBean;
import static in.thedevguys.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.thedevguys.IntegrationTest;
import in.thedevguys.domain.Badge;
import in.thedevguys.domain.enumeration.BadgeType;
import in.thedevguys.repository.BadgeRepository;
import in.thedevguys.service.dto.BadgeDTO;
import in.thedevguys.service.mapper.BadgeMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BadgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BadgeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BadgeType DEFAULT_TYPE = BadgeType.ACHIEVEMENT;
    private static final BadgeType UPDATED_TYPE = BadgeType.MILESTONE;

    private static final String DEFAULT_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_CRITERIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/badges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private BadgeMapper badgeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeMockMvc;

    private Badge badge;

    private Badge insertedBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createEntity() {
        return new Badge()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .type(DEFAULT_TYPE)
            .criteria(DEFAULT_CRITERIA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createUpdatedEntity() {
        return new Badge()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .type(UPDATED_TYPE)
            .criteria(UPDATED_CRITERIA);
    }

    @BeforeEach
    public void initTest() {
        badge = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBadge != null) {
            badgeRepository.delete(insertedBadge);
            insertedBadge = null;
        }
    }

    @Test
    @Transactional
    void createBadge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);
        var returnedBadgeDTO = om.readValue(
            restBadgeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BadgeDTO.class
        );

        // Validate the Badge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBadge = badgeMapper.toEntity(returnedBadgeDTO);
        assertBadgeUpdatableFieldsEquals(returnedBadge, getPersistedBadge(returnedBadge));

        insertedBadge = returnedBadge;
    }

    @Test
    @Transactional
    void createBadgeWithExistingId() throws Exception {
        // Create the Badge with an existing ID
        badge.setId(1L);
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setName(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setDescription(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setImage(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setCreatedAt(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setUpdatedAt(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setType(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCriteriaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        badge.setCriteria(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBadges() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        // Get all the badgeList
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)));
    }

    @Test
    @Transactional
    void getBadge() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        // Get the badge
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL_ID, badge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.criteria").value(DEFAULT_CRITERIA));
    }

    @Test
    @Transactional
    void getNonExistingBadge() throws Exception {
        // Get the badge
        restBadgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBadge() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the badge
        Badge updatedBadge = badgeRepository.findById(badge.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBadge are not directly saved in db
        em.detach(updatedBadge);
        updatedBadge
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .type(UPDATED_TYPE)
            .criteria(UPDATED_CRITERIA);
        BadgeDTO badgeDTO = badgeMapper.toDto(updatedBadge);

        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBadgeToMatchAllProperties(updatedBadge);
    }

    @Test
    @Transactional
    void putNonExistingBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBadgeWithPatch() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the badge using partial update
        Badge partialUpdatedBadge = new Badge();
        partialUpdatedBadge.setId(badge.getId());

        partialUpdatedBadge.name(UPDATED_NAME).criteria(UPDATED_CRITERIA);

        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBadge))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBadgeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBadge, badge), getPersistedBadge(badge));
    }

    @Test
    @Transactional
    void fullUpdateBadgeWithPatch() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the badge using partial update
        Badge partialUpdatedBadge = new Badge();
        partialUpdatedBadge.setId(badge.getId());

        partialUpdatedBadge
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .type(UPDATED_TYPE)
            .criteria(UPDATED_CRITERIA);

        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBadge))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBadgeUpdatableFieldsEquals(partialUpdatedBadge, getPersistedBadge(partialUpdatedBadge));
    }

    @Test
    @Transactional
    void patchNonExistingBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, badgeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBadge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        badge.setId(longCount.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(badgeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Badge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBadge() throws Exception {
        // Initialize the database
        insertedBadge = badgeRepository.saveAndFlush(badge);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the badge
        restBadgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, badge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return badgeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Badge getPersistedBadge(Badge badge) {
        return badgeRepository.findById(badge.getId()).orElseThrow();
    }

    protected void assertPersistedBadgeToMatchAllProperties(Badge expectedBadge) {
        assertBadgeAllPropertiesEquals(expectedBadge, getPersistedBadge(expectedBadge));
    }

    protected void assertPersistedBadgeToMatchUpdatableProperties(Badge expectedBadge) {
        assertBadgeAllUpdatablePropertiesEquals(expectedBadge, getPersistedBadge(expectedBadge));
    }
}
