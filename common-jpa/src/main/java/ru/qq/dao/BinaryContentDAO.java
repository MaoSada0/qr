package ru.qq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qq.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
