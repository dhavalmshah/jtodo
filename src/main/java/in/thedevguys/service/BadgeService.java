package in.thedevguys.service;

import in.thedevguys.domain.Badge;
import in.thedevguys.repository.BadgeRepository;
import in.thedevguys.service.dto.BadgeDTO;
import in.thedevguys.service.mapper.BadgeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link in.thedevguys.domain.Badge}.
 */
@Service
@Transactional
public class BadgeService {

    private static final Logger LOG = LoggerFactory.getLogger(BadgeService.class);

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    public BadgeService(BadgeRepository badgeRepository, BadgeMapper badgeMapper) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
    }

    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    public BadgeDTO save(BadgeDTO badgeDTO) {
        LOG.debug("Request to save Badge : {}", badgeDTO);
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        return badgeMapper.toDto(badge);
    }

    /**
     * Update a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    public BadgeDTO update(BadgeDTO badgeDTO) {
        LOG.debug("Request to update Badge : {}", badgeDTO);
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        return badgeMapper.toDto(badge);
    }

    /**
     * Partially update a badge.
     *
     * @param badgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BadgeDTO> partialUpdate(BadgeDTO badgeDTO) {
        LOG.debug("Request to partially update Badge : {}", badgeDTO);

        return badgeRepository
            .findById(badgeDTO.getId())
            .map(existingBadge -> {
                badgeMapper.partialUpdate(existingBadge, badgeDTO);

                return existingBadge;
            })
            .map(badgeRepository::save)
            .map(badgeMapper::toDto);
    }

    /**
     * Get all the badges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BadgeDTO> findAll() {
        LOG.debug("Request to get all Badges");
        return badgeRepository.findAll().stream().map(badgeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one badge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BadgeDTO> findOne(Long id) {
        LOG.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id).map(badgeMapper::toDto);
    }

    /**
     * Delete the badge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
