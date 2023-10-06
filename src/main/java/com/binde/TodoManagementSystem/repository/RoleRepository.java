package com.binde.TodoManagementSystem.repository;

import com.binde.TodoManagementSystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role  findByName(String name);
}
