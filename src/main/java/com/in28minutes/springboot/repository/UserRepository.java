package com.in28minutes.springboot.repository;

import com.in28minutes.springboot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}