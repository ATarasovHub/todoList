package com.example.todolist.controller;

import com.example.todolist.model.TaskEditRequest;
import lombok.RequiredArgsConstructor;
import com.example.todolist.model.Task;
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

  @GetMapping("/allTasks")
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
//    log.info("Task created{}", task);
    Task addTAsk = taskService.saveTask(task);
    return ResponseEntity.status(HttpStatus.OK).body(addTAsk);
  }

  @PostMapping("/edit")
  public void editTask(@RequestBody TaskEditRequest taskEditRequest){
    taskService.editTask(taskEditRequest);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    Task task = taskService.getTaskById(id);
//    log.info("Task found: {}", task);
    return ResponseEntity.ok(task);
  }

}
