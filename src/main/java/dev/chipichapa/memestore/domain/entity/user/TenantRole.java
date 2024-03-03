package dev.chipichapa.memestore.domain.entity.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tenant_roles")
public class TenantRole {

    @Id
    private int id;
    private String role;

}
