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
 * A UserAttributes.
 */
@Entity
@Table(name = "user_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "email_verified")
    private ZonedDateTime emailVerified;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "level", nullable = false)
    private Long level;

    @NotNull
    @Column(name = "points", nullable = false)
    private Long points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "todo" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "todos", "owner", "members" }, allowSetters = true)
    private Set<Project> projectsOwneds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "subTasks", "comments", "attachments", "notifications", "tags", "creator", "project", "parent", "assignedUsers" },
        allowSetters = true
    )
    private Set<Todo> todosCreateds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "task" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uploader")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "uploader", "todo" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_user_attributes__assigned_todos",
        joinColumns = @JoinColumn(name = "user_attributes_id"),
        inverseJoinColumns = @JoinColumn(name = "assigned_todos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "subTasks", "comments", "attachments", "notifications", "tags", "creator", "project", "parent", "assignedUsers" },
        allowSetters = true
    )
    private Set<Todo> assignedTodos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_user_attributes__badges",
        joinColumns = @JoinColumn(name = "user_attributes_id"),
        inverseJoinColumns = @JoinColumn(name = "badges_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private Set<Badge> badges = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_user_attributes__project_members",
        joinColumns = @JoinColumn(name = "user_attributes_id"),
        inverseJoinColumns = @JoinColumn(name = "project_members_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "todos", "owner", "members" }, allowSetters = true)
    private Set<Project> projectMembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public UserAttributes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public UserAttributes email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getEmailVerified() {
        return this.emailVerified;
    }

    public UserAttributes emailVerified(ZonedDateTime emailVerified) {
        this.setEmailVerified(emailVerified);
        return this;
    }

    public void setEmailVerified(ZonedDateTime emailVerified) {
        this.emailVerified = emailVerified;
    }

    public byte[] getImage() {
        return this.image;
    }

    public UserAttributes image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public UserAttributes imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public UserAttributes createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public UserAttributes updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return this.password;
    }

    public UserAttributes password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLevel() {
        return this.level;
    }

    public UserAttributes level(Long level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getPoints() {
        return this.points;
    }

    public UserAttributes points(Long points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAttributes user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setUser(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setUser(this));
        }
        this.comments = comments;
    }

    public UserAttributes comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public UserAttributes addComments(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
        return this;
    }

    public UserAttributes removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
        return this;
    }

    public Set<Project> getProjectsOwneds() {
        return this.projectsOwneds;
    }

    public void setProjectsOwneds(Set<Project> projects) {
        if (this.projectsOwneds != null) {
            this.projectsOwneds.forEach(i -> i.setOwner(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setOwner(this));
        }
        this.projectsOwneds = projects;
    }

    public UserAttributes projectsOwneds(Set<Project> projects) {
        this.setProjectsOwneds(projects);
        return this;
    }

    public UserAttributes addProjectsOwned(Project project) {
        this.projectsOwneds.add(project);
        project.setOwner(this);
        return this;
    }

    public UserAttributes removeProjectsOwned(Project project) {
        this.projectsOwneds.remove(project);
        project.setOwner(null);
        return this;
    }

    public Set<Todo> getTodosCreateds() {
        return this.todosCreateds;
    }

    public void setTodosCreateds(Set<Todo> todos) {
        if (this.todosCreateds != null) {
            this.todosCreateds.forEach(i -> i.setCreator(null));
        }
        if (todos != null) {
            todos.forEach(i -> i.setCreator(this));
        }
        this.todosCreateds = todos;
    }

    public UserAttributes todosCreateds(Set<Todo> todos) {
        this.setTodosCreateds(todos);
        return this;
    }

    public UserAttributes addTodosCreated(Todo todo) {
        this.todosCreateds.add(todo);
        todo.setCreator(this);
        return this;
    }

    public UserAttributes removeTodosCreated(Todo todo) {
        this.todosCreateds.remove(todo);
        todo.setCreator(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setUser(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setUser(this));
        }
        this.notifications = notifications;
    }

    public UserAttributes notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public UserAttributes addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setUser(this);
        return this;
    }

    public UserAttributes removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setUser(null);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setUploader(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setUploader(this));
        }
        this.attachments = attachments;
    }

    public UserAttributes attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public UserAttributes addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setUploader(this);
        return this;
    }

    public UserAttributes removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setUploader(null);
        return this;
    }

    public Set<Todo> getAssignedTodos() {
        return this.assignedTodos;
    }

    public void setAssignedTodos(Set<Todo> todos) {
        this.assignedTodos = todos;
    }

    public UserAttributes assignedTodos(Set<Todo> todos) {
        this.setAssignedTodos(todos);
        return this;
    }

    public UserAttributes addAssignedTodos(Todo todo) {
        this.assignedTodos.add(todo);
        return this;
    }

    public UserAttributes removeAssignedTodos(Todo todo) {
        this.assignedTodos.remove(todo);
        return this;
    }

    public Set<Badge> getBadges() {
        return this.badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public UserAttributes badges(Set<Badge> badges) {
        this.setBadges(badges);
        return this;
    }

    public UserAttributes addBadges(Badge badge) {
        this.badges.add(badge);
        return this;
    }

    public UserAttributes removeBadges(Badge badge) {
        this.badges.remove(badge);
        return this;
    }

    public Set<Project> getProjectMembers() {
        return this.projectMembers;
    }

    public void setProjectMembers(Set<Project> projects) {
        this.projectMembers = projects;
    }

    public UserAttributes projectMembers(Set<Project> projects) {
        this.setProjectMembers(projects);
        return this;
    }

    public UserAttributes addProjectMembers(Project project) {
        this.projectMembers.add(project);
        return this;
    }

    public UserAttributes removeProjectMembers(Project project) {
        this.projectMembers.remove(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAttributes)) {
            return false;
        }
        return getId() != null && getId().equals(((UserAttributes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAttributes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", emailVerified='" + getEmailVerified() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", password='" + getPassword() + "'" +
            ", level=" + getLevel() +
            ", points=" + getPoints() +
            "}";
    }
}
