package com.github.gabrielspk.cadastro_os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielspk.cadastro_os.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
