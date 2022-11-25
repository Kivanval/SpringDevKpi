package com.example.springdevkpi.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TOPICS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    Set<Post> posts = new LinkedHashSet<>();

    public void addPost(Post post) {
        Objects.requireNonNull(post, "Impossible to add null values to relations");
        posts.add(post);
        post.setTopic(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Topic topic = (Topic) o;
        return id != null && Objects.equals(id, topic.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
