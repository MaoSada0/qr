package ru.qq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.qq.entity.QRCodeUser;

import java.util.Optional;

@Repository
public interface QRCodeUserRepository extends JpaRepository<QRCodeUser, Long> {

    Optional<QRCodeUser> findByUsername(String username);
}
