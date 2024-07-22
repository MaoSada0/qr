package ru.qq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qq.entity.QRCode;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
}
