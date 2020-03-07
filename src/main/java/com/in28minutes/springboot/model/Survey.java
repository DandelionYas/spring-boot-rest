package com.in28minutes.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Survey {
    private String id;
    private String title;
    private String description;
    private List<Question> questions;

    public Survey(String id, String title, String description,
                  List<Question> questions) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
}