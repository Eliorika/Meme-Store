package dev.chipichapa.memestore.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "drafts")
public class Draft {
    @Id
    private UUID id;

    @CreationTimestamp
    private Date createdAt;

}
