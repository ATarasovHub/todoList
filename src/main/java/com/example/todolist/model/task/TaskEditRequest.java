package com.example.todolist.model.task;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
public class TaskEditRequest {
    private String name;
    private LocalDateTime deadline;


}
