package com.example.springdevkpi.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "ROLE_HIERARCHY",
            joinColumns =  @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    @ToString.Exclude
    private Set<Role> childRoles = new HashSet<>();

    public Set<Role> getAllChildRoles() {
        Set<Role> copyChildRoles = new HashSet<>(childRoles);
        Set<Role> allChildRoles = new HashSet<>(copyChildRoles);
        while (!copyChildRoles.isEmpty()) {
            copyChildRoles = allChildRoles.stream()
                    .flatMap(role -> role.getChildRoles().stream())
                    .peek(allChildRoles::add)
                    .filter(role -> !role.getChildRoles().isEmpty())
                    .collect(Collectors.toSet());
        }
        return allChildRoles;
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
