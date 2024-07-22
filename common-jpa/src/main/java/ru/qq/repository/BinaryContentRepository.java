package ru.qq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.qq.entity.BinaryContent;

@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, Long> {
}
