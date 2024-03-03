package dev.chipichapa.memestore.domain.entity;

import dev.chipichapa.memestore.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "images")
@Accessors(chain = true)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid", unique = true)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private String extension;

    //TODO Пересмотреть
    private String status;

    @OneToMany(mappedBy = "image")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ImageTag> imageTags;

}
