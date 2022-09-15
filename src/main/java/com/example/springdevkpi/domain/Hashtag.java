package com.example.springdevkpi.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name =  "HASHTAGS")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "hashtags")
    @ToString.Exclude
    private Set<Topic> topics = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hashtag hashtag = (Hashtag) o;
        return id != null && Objects.equals(id, hashtag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
