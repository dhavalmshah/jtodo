package in.thedevguys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @Column(name = "color")
    private String color;

    @Column(name = "icon")
    private String icon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "subTasks", "comments", "attachments", "notifications", "tags", "creator", "project", "parent", "assignedUsers" },
        allowSetters = true
    )
    private Set<Todo> todos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
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
    private UserAttributes owner;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projectMembers")
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
    private Set<UserAttributes> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Project description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Project createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Project updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getColor() {
        return this.color;
    }

    public Project color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return this.icon;
    }

    public Project icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Todo> getTodos() {
        return this.todos;
    }

    public void setTodos(Set<Todo> todos) {
        if (this.todos != null) {
            this.todos.forEach(i -> i.setProject(null));
        }
        if (todos != null) {
            todos.forEach(i -> i.setProject(this));
        }
        this.todos = todos;
    }

    public Project todos(Set<Todo> todos) {
        this.setTodos(todos);
        return this;
    }

    public Project addTodos(Todo todo) {
        this.todos.add(todo);
        todo.setProject(this);
        return this;
    }

    public Project removeTodos(Todo todo) {
        this.todos.remove(todo);
        todo.setProject(null);
        return this;
    }

    public UserAttributes getOwner() {
        return this.owner;
    }

    public void setOwner(UserAttributes userAttributes) {
        this.owner = userAttributes;
    }

    public Project owner(UserAttributes userAttributes) {
        this.setOwner(userAttributes);
        return this;
    }

    public Set<UserAttributes> getMembers() {
        return this.members;
    }

    public void setMembers(Set<UserAttributes> userAttributes) {
        if (this.members != null) {
            this.members.forEach(i -> i.removeProjectMembers(this));
        }
        if (userAttributes != null) {
            userAttributes.forEach(i -> i.addProjectMembers(this));
        }
        this.members = userAttributes;
    }

    public Project members(Set<UserAttributes> userAttributes) {
        this.setMembers(userAttributes);
        return this;
    }

    public Project addMembers(UserAttributes userAttributes) {
        this.members.add(userAttributes);
        userAttributes.getProjectMembers().add(this);
        return this;
    }

    public Project removeMembers(UserAttributes userAttributes) {
        this.members.remove(userAttributes);
        userAttributes.getProjectMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", color='" + getColor() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
