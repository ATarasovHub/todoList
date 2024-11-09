package com.example.todolist.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskRecord {
    private Long id;

    private String name;

    private LocalDateTime deadline;


}
