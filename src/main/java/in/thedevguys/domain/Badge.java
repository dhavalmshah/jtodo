package in.thedevguys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.thedevguys.domain.enumeration.BadgeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Badge.
 */
@Entity
@Table(name = "badge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BadgeType type;

    @NotNull
    @Column(name = "criteria", nullable = false)
    private String criteria;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "badges")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "user",
            "comments",
            "projectsOwneds",
            "todosCreateds",
            "notifications",
            "attachments",
            "assignedTodos",
            "badges",
            "projectMembers",
        },
        allowSetters = true
    )
    private Set<UserAttributes> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Badge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Badge name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Badge description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public Badge image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Badge createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Badge updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BadgeType getType() {
        return this.type;
    }

    public Badge type(BadgeType type) {
        this.setType(type);
        return this;
    }

    public void setType(BadgeType type) {
        this.type = type;
    }

    public String getCriteria() {
        return this.criteria;
    }

    public Badge criteria(String criteria) {
        this.setCriteria(criteria);
        return this;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Set<UserAttributes> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserAttributes> userAttributes) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeBadges(this));
        }
        if (userAttributes != null) {
            userAttributes.forEach(i -> i.addBadges(this));
        }
        this.users = userAttributes;
    }

    public Badge users(Set<UserAttributes> userAttributes) {
        this.setUsers(userAttributes);
        return this;
    }

    public Badge addUsers(UserAttributes userAttributes) {
        this.users.add(userAttributes);
        userAttributes.getBadges().add(this);
        return this;
    }

    public Badge removeUsers(UserAttributes userAttributes) {
        this.users.remove(userAttributes);
        userAttributes.getBadges().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Badge)) {
            return false;
        }
        return getId() != null && getId().equals(((Badge) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", type='" + getType() + "'" +
            ", criteria='" + getCriteria() + "'" +
            "}";
    }
}
