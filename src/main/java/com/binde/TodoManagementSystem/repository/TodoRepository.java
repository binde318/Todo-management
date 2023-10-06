package com.binde.TodoManagementSystem.repository;

import com.binde.TodoManagementSystem.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
