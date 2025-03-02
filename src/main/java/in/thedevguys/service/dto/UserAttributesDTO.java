package in.thedevguys.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link in.thedevguys.domain.UserAttributes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAttributesDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private ZonedDateTime emailVerified;

    @Lob
    private byte[] image;

    private String imageContentType;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private ZonedDateTime updatedAt;

    private String password;

    @NotNull
    private Long level;

    @NotNull
    private Long points;

    private UserDTO user;

    private Set<TodoDTO> assignedTodos = new HashSet<>();

    private Set<BadgeDTO> badges = new HashSet<>();

    private Set<ProjectDTO> projectMembers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(ZonedDateTime emailVerified) {
        this.emailVerified = emailVerified;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Set<TodoDTO> getAssignedTodos() {
        return assignedTodos;
    }

    public void setAssignedTodos(Set<TodoDTO> assignedTodos) {
        this.assignedTodos = assignedTodos;
    }

    public Set<BadgeDTO> getBadges() {
        return badges;
    }

    public void setBadges(Set<BadgeDTO> badges) {
        this.badges = badges;
    }

    public Set<ProjectDTO> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(Set<ProjectDTO> projectMembers) {
        this.projectMembers = projectMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAttributesDTO)) {
            return false;
        }

        UserAttributesDTO userAttributesDTO = (UserAttributesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAttributesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAttributesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", emailVerified='" + getEmailVerified() + "'" +
            ", image='" + getImage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", password='" + getPassword() + "'" +
            ", level=" + getLevel() +
            ", points=" + getPoints() +
            ", user=" + getUser() +
            ", assignedTodos=" + getAssignedTodos() +
            ", badges=" + getBadges() +
            ", projectMembers=" + getProjectMembers() +
            "}";
    }
}
