package com.example.todolist.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class TaskEditRequest {
    private Long id;
    private String name;
    private LocalDateTime deadline;
}
