package dev.chipichapa.memestore.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "image_tags", schema = "public")
public class ImageTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    private Integer score;

    // Constructors, getters, and setters
}
