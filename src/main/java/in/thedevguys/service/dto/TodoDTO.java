package in.thedevguys.service.dto;

import in.thedevguys.domain.enumeration.Priority;
import in.thedevguys.domain.enumeration.TodoStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link in.thedevguys.domain.Todo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TodoDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private ZonedDateTime dueDate;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private ZonedDateTime updatedAt;

    @NotNull
    private TodoStatus status;

    @NotNull
    private Priority priority;

    private Long pointsAwarded;

    private Set<TagDTO> tags = new HashSet<>();

    private AdminUserDTO creator;

    private ProjectDTO project;

    private TodoDTO parent;

    private Set<AdminUserDTO> assignedUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(Long pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public AdminUserDTO getCreator() {
        return creator;
    }

    public void setCreator(AdminUserDTO creator) {
        this.creator = creator;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public TodoDTO getParent() {
        return parent;
    }

    public void setParent(TodoDTO parent) {
        this.parent = parent;
    }

    public Set<AdminUserDTO> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<AdminUserDTO> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TodoDTO)) {
            return false;
        }

        TodoDTO todoDTO = (TodoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, todoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TodoDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", pointsAwarded=" + getPointsAwarded() +
            ", tags=" + getTags() +
            ", creator=" + getCreator() +
            ", project=" + getProject() +
            ", parent=" + getParent() +
            ", assignedUsers=" + getAssignedUsers() +
            "}";
    }
}
