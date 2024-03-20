package dev.chipichapa.memestore.ocr.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "ocr_tokens")
@EqualsAndHashCode(exclude = {"id"})
@Accessors(chain = true)
public class OcrToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "expired_at")
    private Timestamp expiredAt;
}
