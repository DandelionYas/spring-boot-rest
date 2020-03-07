package com.in28minutes.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Question {
    private String id;
    private String description;
    private String correctAnswer;
    private List<String> options;
    // No argument constructor created by lombok
    // Needed by Caused by: com.fasterxml.jackson.databind.JsonMappingException:
    // Can not construct instance of com.in28minutes.springboot.model.Question:
    // no suitable constructor found, can not deserialize from Object value
    // (missing default constructor or creator, or perhaps need to add/enable
    // type information?)

    public Question(String id, String description, String correctAnswer,
                    List<String> options) {
        super();
        this.id = id;
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }
}