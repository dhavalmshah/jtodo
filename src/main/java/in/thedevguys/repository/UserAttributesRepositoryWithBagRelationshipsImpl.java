package in.thedevguys.repository;

import in.thedevguys.domain.UserAttributes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserAttributesRepositoryWithBagRelationshipsImpl implements UserAttributesRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String USERATTRIBUTES_PARAMETER = "userAttributes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserAttributes> fetchBagRelationships(Optional<UserAttributes> userAttributes) {
        return userAttributes.map(this::fetchAssignedTodos).map(this::fetchBadges).map(this::fetchProjectMembers);
    }

    @Override
    public Page<UserAttributes> fetchBagRelationships(Page<UserAttributes> userAttributes) {
        return new PageImpl<>(
            fetchBagRelationships(userAttributes.getContent()),
            userAttributes.getPageable(),
            userAttributes.getTotalElements()
        );
    }

    @Override
    public List<UserAttributes> fetchBagRelationships(List<UserAttributes> userAttributes) {
        return Optional.of(userAttributes)
            .map(this::fetchAssignedTodos)
            .map(this::fetchBadges)
            .map(this::fetchProjectMembers)
            .orElse(Collections.emptyList());
    }

    UserAttributes fetchAssignedTodos(UserAttributes result) {
        return entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.assignedTodos where userAttributes.id = :id",
                UserAttributes.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<UserAttributes> fetchAssignedTodos(List<UserAttributes> userAttributes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userAttributes.size()).forEach(index -> order.put(userAttributes.get(index).getId(), index));
        List<UserAttributes> result = entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.assignedTodos where userAttributes in :userAttributes",
                UserAttributes.class
            )
            .setParameter(USERATTRIBUTES_PARAMETER, userAttributes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    UserAttributes fetchBadges(UserAttributes result) {
        return entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.badges where userAttributes.id = :id",
                UserAttributes.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<UserAttributes> fetchBadges(List<UserAttributes> userAttributes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userAttributes.size()).forEach(index -> order.put(userAttributes.get(index).getId(), index));
        List<UserAttributes> result = entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.badges where userAttributes in :userAttributes",
                UserAttributes.class
            )
            .setParameter(USERATTRIBUTES_PARAMETER, userAttributes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    UserAttributes fetchProjectMembers(UserAttributes result) {
        return entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.projectMembers where userAttributes.id = :id",
                UserAttributes.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<UserAttributes> fetchProjectMembers(List<UserAttributes> userAttributes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userAttributes.size()).forEach(index -> order.put(userAttributes.get(index).getId(), index));
        List<UserAttributes> result = entityManager
            .createQuery(
                "select userAttributes from UserAttributes userAttributes left join fetch userAttributes.projectMembers where userAttributes in :userAttributes",
                UserAttributes.class
            )
            .setParameter(USERATTRIBUTES_PARAMETER, userAttributes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
