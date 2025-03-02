package in.thedevguys.repository;

import in.thedevguys.domain.UserAttributes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserAttributesRepositoryWithBagRelationships {
    Optional<UserAttributes> fetchBagRelationships(Optional<UserAttributes> userAttributes);

    List<UserAttributes> fetchBagRelationships(List<UserAttributes> userAttributes);

    Page<UserAttributes> fetchBagRelationships(Page<UserAttributes> userAttributes);
}
