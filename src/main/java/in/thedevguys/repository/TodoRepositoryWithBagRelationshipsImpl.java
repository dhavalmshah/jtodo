package in.thedevguys.repository;

import in.thedevguys.domain.Todo;
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
public class TodoRepositoryWithBagRelationshipsImpl implements TodoRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TODOS_PARAMETER = "todos";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Todo> fetchBagRelationships(Optional<Todo> todo) {
        return todo.map(this::fetchTags);
    }

    @Override
    public Page<Todo> fetchBagRelationships(Page<Todo> todos) {
        return new PageImpl<>(fetchBagRelationships(todos.getContent()), todos.getPageable(), todos.getTotalElements());
    }

    @Override
    public List<Todo> fetchBagRelationships(List<Todo> todos) {
        return Optional.of(todos).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Todo fetchTags(Todo result) {
        return entityManager
            .createQuery("select todo from Todo todo left join fetch todo.tags where todo.id = :id", Todo.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Todo> fetchTags(List<Todo> todos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, todos.size()).forEach(index -> order.put(todos.get(index).getId(), index));
        List<Todo> result = entityManager
            .createQuery("select todo from Todo todo left join fetch todo.tags where todo in :todos", Todo.class)
            .setParameter(TODOS_PARAMETER, todos)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
