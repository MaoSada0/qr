package ru.qq.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qq.model.entity.QRCode;

public interface QRCodeDAO extends JpaRepository<QRCode, Long> {
}
