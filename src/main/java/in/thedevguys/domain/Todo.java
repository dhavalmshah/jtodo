package in.thedevguys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.thedevguys.domain.enumeration.Priority;
import in.thedevguys.domain.enumeration.TodoStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Todo.
 */
@Entity
@Table(name = "todo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TodoStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "points_awarded")
    private Long pointsAwarded;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "subTasks", "comments", "attachments", "notifications", "tags", "creator", "project", "parent", "assignedUsers" },
        allowSetters = true
    )
    private Set<Todo> subTasks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "todo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "todo" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "todo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "uploader", "todo" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "task" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_todo__tags", joinColumns = @JoinColumn(name = "todo_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "todos" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

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
    private UserAttributes creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "todos", "owner", "members" }, allowSetters = true)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "subTasks", "comments", "attachments", "notifications", "tags", "creator", "project", "parent", "assignedUsers" },
        allowSetters = true
    )
    private Todo parent;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assignedTodos")
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
    private Set<UserAttributes> assignedUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Todo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Todo title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Todo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDueDate() {
        return this.dueDate;
    }

    public Todo dueDate(ZonedDateTime dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Todo createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Todo updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TodoStatus getStatus() {
        return this.status;
    }

    public Todo status(TodoStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Todo priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getPointsAwarded() {
        return this.pointsAwarded;
    }

    public Todo pointsAwarded(Long pointsAwarded) {
        this.setPointsAwarded(pointsAwarded);
        return this;
    }

    public void setPointsAwarded(Long pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public Set<Todo> getSubTasks() {
        return this.subTasks;
    }

    public void setSubTasks(Set<Todo> todos) {
        if (this.subTasks != null) {
            this.subTasks.forEach(i -> i.setParent(null));
        }
        if (todos != null) {
            todos.forEach(i -> i.setParent(this));
        }
        this.subTasks = todos;
    }

    public Todo subTasks(Set<Todo> todos) {
        this.setSubTasks(todos);
        return this;
    }

    public Todo addSubTasks(Todo todo) {
        this.subTasks.add(todo);
        todo.setParent(this);
        return this;
    }

    public Todo removeSubTasks(Todo todo) {
        this.subTasks.remove(todo);
        todo.setParent(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setTodo(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setTodo(this));
        }
        this.comments = comments;
    }

    public Todo comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Todo addComments(Comment comment) {
        this.comments.add(comment);
        comment.setTodo(this);
        return this;
    }

    public Todo removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setTodo(null);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setTodo(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setTodo(this));
        }
        this.attachments = attachments;
    }

    public Todo attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Todo addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setTodo(this);
        return this;
    }

    public Todo removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setTodo(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setTask(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setTask(this));
        }
        this.notifications = notifications;
    }

    public Todo notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public Todo addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setTask(this);
        return this;
    }

    public Todo removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setTask(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Todo tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Todo addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Todo removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public UserAttributes getCreator() {
        return this.creator;
    }

    public void setCreator(UserAttributes userAttributes) {
        this.creator = userAttributes;
    }

    public Todo creator(UserAttributes userAttributes) {
        this.setCreator(userAttributes);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Todo project(Project project) {
        this.setProject(project);
        return this;
    }

    public Todo getParent() {
        return this.parent;
    }

    public void setParent(Todo todo) {
        this.parent = todo;
    }

    public Todo parent(Todo todo) {
        this.setParent(todo);
        return this;
    }

    public Set<UserAttributes> getAssignedUsers() {
        return this.assignedUsers;
    }

    public void setAssignedUsers(Set<UserAttributes> userAttributes) {
        if (this.assignedUsers != null) {
            this.assignedUsers.forEach(i -> i.removeAssignedTodos(this));
        }
        if (userAttributes != null) {
            userAttributes.forEach(i -> i.addAssignedTodos(this));
        }
        this.assignedUsers = userAttributes;
    }

    public Todo assignedUsers(Set<UserAttributes> userAttributes) {
        this.setAssignedUsers(userAttributes);
        return this;
    }

    public Todo addAssignedUsers(UserAttributes userAttributes) {
        this.assignedUsers.add(userAttributes);
        userAttributes.getAssignedTodos().add(this);
        return this;
    }

    public Todo removeAssignedUsers(UserAttributes userAttributes) {
        this.assignedUsers.remove(userAttributes);
        userAttributes.getAssignedTodos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Todo)) {
            return false;
        }
        return getId() != null && getId().equals(((Todo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Todo{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", pointsAwarded=" + getPointsAwarded() +
            "}";
    }
}
