package org.aibles.java.demologin.repository;
import org.aibles.java.demologin.model.ERole;
import org.aibles.java.demologin.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long > {
    Optional<Role> findByName(ERole name);
}
