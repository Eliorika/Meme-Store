package dev.chipichapa.memestore.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

import java.util.UUID;


@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "drafts")
public class Draft {
    @Id
    @Column(name = "uuid")
    private UUID id;

    @CreationTimestamp
    private Date createdAt;

}
