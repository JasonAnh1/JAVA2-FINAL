package com.samsung.bookmanagerment.repository.user;

import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    User findUserById(long id);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    Boolean existsByName(String name);




}
