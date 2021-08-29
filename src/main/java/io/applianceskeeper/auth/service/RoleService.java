package io.applianceskeeper.auth.service;

import io.applianceskeeper.auth.data.RoleRepository;
import io.applianceskeeper.auth.models.ERole;
import io.applianceskeeper.auth.models.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository repository;

    public Optional<Role> findByName(ERole roleAdmin) {
        return repository.findByName(roleAdmin);
    }
}
