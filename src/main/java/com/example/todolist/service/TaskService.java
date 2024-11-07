package com.example.todolist.service;

import com.example.todolist.model.task.Task;
import com.example.todolist.model.task.TaskEditRequest;
import com.example.todolist.model.task.TaskRecord;
import com.example.todolist.repository.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public List<TaskRecord> getAllTasks() {
    return taskRepository.findAll().stream().map(this::toTaskRecord).collect(Collectors.toList());
  }

  public TaskRecord saveTask(TaskRecord tasksModel) {
    return toTaskRecord(taskRepository.save(toTask(tasksModel)));
  }

  public void editTask(Long id, TaskEditRequest taskEditRequest) {
    Task task =
        taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    task.setName(taskEditRequest.getName());
    task.setDeadline(taskEditRequest.getDeadline());

    taskRepository.save(task);
  }

  public TaskRecord getTaskById(Long id) {
    return toTaskRecord(
        taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found")));
  }

  public TaskRecord toTaskRecord(Task task) {
    return TaskRecord.builder()
        .id(task.getId())
        .name(task.getName())
        .deadline(task.getDeadline())
        .build();
  }

  public Task toTask(TaskRecord taskRecord) {
    return Task.builder()
        .id(taskRecord.getId())
        .name(taskRecord.getName())
        .deadline(taskRecord.getDeadline())
        .build();
  }
}
