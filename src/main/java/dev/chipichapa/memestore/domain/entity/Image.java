package dev.chipichapa.memestore.domain.entity;

import dev.chipichapa.memestore.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;
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

    @ManyToMany
    @JoinTable(
            name = "album_images",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Set<Album> albums;

    @OneToMany(mappedBy = "image")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ImageTag> imageTags;

    private String extension;
    private String title;
    private String description;
    private String blurhash;

    public String getFilenameWithExtension(){
        return this.uuid + "." + this.getExtension();
    }

}
