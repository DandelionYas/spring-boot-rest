package com.in28minutes.springboot.repository;

import com.in28minutes.springboot.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByRole(@Param("role") String role);// /users/search/findByRole?role=Admin
}