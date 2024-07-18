package ru.qq.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.qq.entity.BinaryContent;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qrcode")
public class QRCode {
    // I understand that that keeping qr in db is useless, im doing it for practice!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    private Short size;

    @OneToOne
    @JsonIgnore
    private BinaryContent qrBinaryContent;
}
