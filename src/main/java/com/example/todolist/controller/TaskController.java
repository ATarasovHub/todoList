package com.example.todolist.controller;

import com.example.todolist.model.task.TaskEditRequest;
import com.example.todolist.model.task.TaskRecord;
import com.example.todolist.service.TransferService;
import lombok.RequiredArgsConstructor;
import com.example.todolist.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  private final TransferService transferService;

  @GetMapping
  public List<TaskRecord> getAllTasks() {
    return taskService.getAllTasks();
  }

  @PostMapping
  public ResponseEntity<TaskRecord> createTask(@RequestBody TaskRecord task) {
    //    log.info("Task created{}", task);
    TaskRecord addTAsk = taskService.saveTask(task);
    return ResponseEntity.status(HttpStatus.OK).body(addTAsk);
  }

  @PutMapping("/{id}/edit")
  public void editTask(@PathVariable Long id, @RequestBody TaskEditRequest taskEditRequest) {
    taskService.editTask(id, taskEditRequest);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskRecord> getTaskById(@PathVariable Long id) {
    TaskRecord task = taskService.getTaskById(id);
    //    log.info("Task found: {}", task);
    return ResponseEntity.ok(task);
  }

  @PostMapping("/{id}/history")
  public void transferRowController(@PathVariable Long id) {
    // transferService.transferRow(request.getId());
    transferService.transferRow(id);
  }
}
