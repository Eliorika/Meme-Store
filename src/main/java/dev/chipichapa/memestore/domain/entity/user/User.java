package dev.chipichapa.memestore.domain.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    private String displayName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "album_images",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<TenantRole> tenantRoles;

    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    private Boolean isExtremist;



    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }
}
