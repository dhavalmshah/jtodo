package in.thedevguys.service;

import in.thedevguys.domain.UserAttributes;
import in.thedevguys.repository.UserAttributesRepository;
import in.thedevguys.service.dto.UserAttributesDTO;
import in.thedevguys.service.mapper.UserAttributesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link in.thedevguys.domain.UserAttributes}.
 */
@Service
@Transactional
public class UserAttributesService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAttributesService.class);

    private final UserAttributesRepository userAttributesRepository;

    private final UserAttributesMapper userAttributesMapper;

    public UserAttributesService(UserAttributesRepository userAttributesRepository, UserAttributesMapper userAttributesMapper) {
        this.userAttributesRepository = userAttributesRepository;
        this.userAttributesMapper = userAttributesMapper;
    }

    /**
     * Save a userAttributes.
     *
     * @param userAttributesDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAttributesDTO save(UserAttributesDTO userAttributesDTO) {
        LOG.debug("Request to save UserAttributes : {}", userAttributesDTO);
        UserAttributes userAttributes = userAttributesMapper.toEntity(userAttributesDTO);
        userAttributes = userAttributesRepository.save(userAttributes);
        return userAttributesMapper.toDto(userAttributes);
    }

    /**
     * Update a userAttributes.
     *
     * @param userAttributesDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAttributesDTO update(UserAttributesDTO userAttributesDTO) {
        LOG.debug("Request to update UserAttributes : {}", userAttributesDTO);
        UserAttributes userAttributes = userAttributesMapper.toEntity(userAttributesDTO);
        userAttributes = userAttributesRepository.save(userAttributes);
        return userAttributesMapper.toDto(userAttributes);
    }

    /**
     * Partially update a userAttributes.
     *
     * @param userAttributesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAttributesDTO> partialUpdate(UserAttributesDTO userAttributesDTO) {
        LOG.debug("Request to partially update UserAttributes : {}", userAttributesDTO);

        return userAttributesRepository
            .findById(userAttributesDTO.getId())
            .map(existingUserAttributes -> {
                userAttributesMapper.partialUpdate(existingUserAttributes, userAttributesDTO);

                return existingUserAttributes;
            })
            .map(userAttributesRepository::save)
            .map(userAttributesMapper::toDto);
    }

    /**
     * Get all the userAttributes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserAttributesDTO> findAll() {
        LOG.debug("Request to get all UserAttributes");
        return userAttributesRepository
            .findAll()
            .stream()
            .map(userAttributesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the userAttributes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserAttributesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userAttributesRepository.findAllWithEagerRelationships(pageable).map(userAttributesMapper::toDto);
    }

    /**
     * Get one userAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserAttributesDTO> findOne(Long id) {
        LOG.debug("Request to get UserAttributes : {}", id);
        return userAttributesRepository.findOneWithEagerRelationships(id).map(userAttributesMapper::toDto);
    }

    /**
     * Delete the userAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete UserAttributes : {}", id);
        userAttributesRepository.deleteById(id);
    }
}
