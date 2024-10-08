package com.samsung.bookmanagerment.repository.user;


import com.samsung.bookmanagerment.entity.Role;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTitle(String roleName);

    Optional<Role> findByName(RoleName roleName);


}
