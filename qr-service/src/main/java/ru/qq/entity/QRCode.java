package ru.qq.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private BinaryContent qrBinaryContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
