package dev.chipichapa.memestore.domain.entity.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
