package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.security.AccessRole;
import com.synrgy.binarfud.Binarfud.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRole(AccessRole accessRole);
}
