package com.example.todolist.service;

import com.example.todolist.model.history.History;
import com.example.todolist.model.task.Task;
import com.example.todolist.repository.HistoryRepository;
import com.example.todolist.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

  private final HistoryRepository historyRepository;

  private final TaskRepository taskRepository;

  @Transactional
  public void transferRow(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));

    History history =
        History.builder()
            .id(task.getId())
            .name(task.getName())
            .deadline(task.getDeadline())
            .build();

    historyRepository.save(history);
    taskRepository.deleteById(id);
  }
}
