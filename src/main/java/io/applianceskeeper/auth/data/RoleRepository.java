package io.applianceskeeper.auth.data;

import io.applianceskeeper.auth.models.ERole;
import io.applianceskeeper.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
