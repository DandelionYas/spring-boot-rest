package com.in28minutes.springboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue // default is Auto
    private Long id;
    private String name;
    private String role;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }
}