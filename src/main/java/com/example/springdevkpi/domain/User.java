package com.example.springdevkpi.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false, unique = true)
    String username;
    String password;
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToMany(mappedBy = "creator")
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    Set<Topic> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "creator")
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    Set<Post> posts = new LinkedHashSet<>();

    public void addTopic(Topic topic) {
        if (topic != null) {
            topics.add(topic);
            topic.setCreator(this);
        }
    }

    public void removeTopic(Topic topic) {
        if (topic != null) {
            topics.remove(topic);
            topic.setCreator(null);
        }
    }

    public void addPost(Post post) {
        if (post != null) {
            posts.add(post);
            post.setCreator(this);
        }
    }

    public void removePost(Post post) {
        if (post != null) {
            posts.remove(post);
            post.setCreator(null);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
