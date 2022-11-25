package com.example.springdevkpi.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    int rank;

    @OneToMany(mappedBy = "role")
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    Set<User> users = new LinkedHashSet<>();

    public void addUser(User user) {
        Objects.requireNonNull(user, "Impossible to add null values to relations");
        users.add(user);
        user.setRole(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
