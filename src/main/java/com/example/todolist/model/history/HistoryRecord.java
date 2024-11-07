package com.example.todolist.model.history;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class HistoryRecord {
  private Long id;

  private String name;

  private LocalDateTime deadline;
}
