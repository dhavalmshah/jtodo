package in.thedevguys.web.rest;

import static in.thedevguys.domain.UserAttributesAsserts.*;
import static in.thedevguys.web.rest.TestUtil.createUpdateProxyForBean;
import static in.thedevguys.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.thedevguys.IntegrationTest;
import in.thedevguys.domain.UserAttributes;
import in.thedevguys.repository.UserAttributesRepository;
import in.thedevguys.repository.UserRepository;
import in.thedevguys.service.UserAttributesService;
import in.thedevguys.service.dto.UserAttributesDTO;
import in.thedevguys.service.mapper.UserAttributesMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserAttributesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserAttributesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EMAIL_VERIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EMAIL_VERIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Long DEFAULT_LEVEL = 1L;
    private static final Long UPDATED_LEVEL = 2L;

    private static final Long DEFAULT_POINTS = 1L;
    private static final Long UPDATED_POINTS = 2L;

    private static final String ENTITY_API_URL = "/api/user-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserAttributesRepository userAttributesRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserAttributesRepository userAttributesRepositoryMock;

    @Autowired
    private UserAttributesMapper userAttributesMapper;

    @Mock
    private UserAttributesService userAttributesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAttributesMockMvc;

    private UserAttributes userAttributes;

    private UserAttributes insertedUserAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributes createEntity() {
        return new UserAttributes()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .emailVerified(DEFAULT_EMAIL_VERIFIED)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .password(DEFAULT_PASSWORD)
            .level(DEFAULT_LEVEL)
            .points(DEFAULT_POINTS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAttributes createUpdatedEntity() {
        return new UserAttributes()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .password(UPDATED_PASSWORD)
            .level(UPDATED_LEVEL)
            .points(UPDATED_POINTS);
    }

    @BeforeEach
    public void initTest() {
        userAttributes = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedUserAttributes != null) {
            userAttributesRepository.delete(insertedUserAttributes);
            insertedUserAttributes = null;
        }
    }

    @Test
    @Transactional
    void createUserAttributes() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);
        var returnedUserAttributesDTO = om.readValue(
            restUserAttributesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UserAttributesDTO.class
        );

        // Validate the UserAttributes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUserAttributes = userAttributesMapper.toEntity(returnedUserAttributesDTO);
        assertUserAttributesUpdatableFieldsEquals(returnedUserAttributes, getPersistedUserAttributes(returnedUserAttributes));

        insertedUserAttributes = returnedUserAttributes;
    }

    @Test
    @Transactional
    void createUserAttributesWithExistingId() throws Exception {
        // Create the UserAttributes with an existing ID
        userAttributes.setId(1L);
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAttributesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        userAttributes.setCreatedAt(null);

        // Create the UserAttributes, which fails.
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        restUserAttributesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        userAttributes.setUpdatedAt(null);

        // Create the UserAttributes, which fails.
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        restUserAttributesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLevelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        userAttributes.setLevel(null);

        // Create the UserAttributes, which fails.
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        restUserAttributesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        userAttributes.setPoints(null);

        // Create the UserAttributes, which fails.
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        restUserAttributesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAttributes() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        // Get all the userAttributesList
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(sameInstant(DEFAULT_EMAIL_VERIFIED))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserAttributesWithEagerRelationshipsIsEnabled() throws Exception {
        when(userAttributesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserAttributesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userAttributesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserAttributesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userAttributesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserAttributesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userAttributesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserAttributes() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        // Get the userAttributes
        restUserAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, userAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAttributes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.emailVerified").value(sameInstant(DEFAULT_EMAIL_VERIFIED)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserAttributes() throws Exception {
        // Get the userAttributes
        restUserAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserAttributes() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userAttributes
        UserAttributes updatedUserAttributes = userAttributesRepository.findById(userAttributes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUserAttributes are not directly saved in db
        em.detach(updatedUserAttributes);
        updatedUserAttributes
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .password(UPDATED_PASSWORD)
            .level(UPDATED_LEVEL)
            .points(UPDATED_POINTS);
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(updatedUserAttributes);

        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAttributesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userAttributesDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUserAttributesToMatchAllProperties(updatedUserAttributes);
    }

    @Test
    @Transactional
    void putNonExistingUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAttributesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userAttributesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userAttributesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAttributesWithPatch() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userAttributes using partial update
        UserAttributes partialUpdatedUserAttributes = new UserAttributes();
        partialUpdatedUserAttributes.setId(userAttributes.getId());

        partialUpdatedUserAttributes.email(UPDATED_EMAIL).emailVerified(UPDATED_EMAIL_VERIFIED).points(UPDATED_POINTS);

        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserAttributesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUserAttributes, userAttributes),
            getPersistedUserAttributes(userAttributes)
        );
    }

    @Test
    @Transactional
    void fullUpdateUserAttributesWithPatch() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userAttributes using partial update
        UserAttributes partialUpdatedUserAttributes = new UserAttributes();
        partialUpdatedUserAttributes.setId(userAttributes.getId());

        partialUpdatedUserAttributes
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .password(UPDATED_PASSWORD)
            .level(UPDATED_LEVEL)
            .points(UPDATED_POINTS);

        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserAttributes))
            )
            .andExpect(status().isOk());

        // Validate the UserAttributes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserAttributesUpdatableFieldsEquals(partialUpdatedUserAttributes, getPersistedUserAttributes(partialUpdatedUserAttributes));
    }

    @Test
    @Transactional
    void patchNonExistingUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAttributesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userAttributesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userAttributesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAttributes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userAttributes.setId(longCount.incrementAndGet());

        // Create the UserAttributes
        UserAttributesDTO userAttributesDTO = userAttributesMapper.toDto(userAttributes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAttributesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(userAttributesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAttributes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAttributes() throws Exception {
        // Initialize the database
        insertedUserAttributes = userAttributesRepository.saveAndFlush(userAttributes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the userAttributes
        restUserAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return userAttributesRepository.count();
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

    protected UserAttributes getPersistedUserAttributes(UserAttributes userAttributes) {
        return userAttributesRepository.findById(userAttributes.getId()).orElseThrow();
    }

    protected void assertPersistedUserAttributesToMatchAllProperties(UserAttributes expectedUserAttributes) {
        assertUserAttributesAllPropertiesEquals(expectedUserAttributes, getPersistedUserAttributes(expectedUserAttributes));
    }

    protected void assertPersistedUserAttributesToMatchUpdatableProperties(UserAttributes expectedUserAttributes) {
        assertUserAttributesAllUpdatablePropertiesEquals(expectedUserAttributes, getPersistedUserAttributes(expectedUserAttributes));
    }
}
