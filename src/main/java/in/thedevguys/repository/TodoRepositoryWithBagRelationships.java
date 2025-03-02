package in.thedevguys.repository;

import in.thedevguys.domain.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TodoRepositoryWithBagRelationships {
    Optional<Todo> fetchBagRelationships(Optional<Todo> todo);

    List<Todo> fetchBagRelationships(List<Todo> todos);

    Page<Todo> fetchBagRelationships(Page<Todo> todos);
}
