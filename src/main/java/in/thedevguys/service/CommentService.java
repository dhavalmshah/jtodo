package in.thedevguys.service;

import in.thedevguys.domain.Comment;
import in.thedevguys.repository.CommentRepository;
import in.thedevguys.service.dto.CommentDTO;
import in.thedevguys.service.mapper.CommentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link in.thedevguys.domain.Comment}.
 */
@Service
@Transactional
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO save(CommentDTO commentDTO) {
        LOG.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Update a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO update(CommentDTO commentDTO) {
        LOG.debug("Request to update Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Partially update a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentDTO> partialUpdate(CommentDTO commentDTO) {
        LOG.debug("Request to partially update Comment : {}", commentDTO);

        return commentRepository
            .findById(commentDTO.getId())
            .map(existingComment -> {
                commentMapper.partialUpdate(existingComment, commentDTO);

                return existingComment;
            })
            .map(commentRepository::save)
            .map(commentMapper::toDto);
    }

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommentDTO> findAll() {
        LOG.debug("Request to get all Comments");
        return commentRepository.findAll().stream().map(commentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        LOG.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }
}
