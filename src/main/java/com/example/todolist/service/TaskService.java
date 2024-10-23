package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskEditRequest;
import com.example.todolist.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class TaskService {

  private final TaskRepository taskRepository;

  @Autowired
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Task saveTask(Task tasksModel) {
    return taskRepository.save(tasksModel);
  }

  @Transactional
  public void clearTaskHistory() {
    taskRepository.deleteAll();
  }

  public void editTask(TaskEditRequest taskEditRequest) {
    Task task = taskRepository.findById(taskEditRequest.getId()).orElseThrow(() -> new RuntimeException("Task not found"));

    Task newTask = new Task();

    newTask.setId(task.getId());
    newTask.setName(taskEditRequest.getName());
    newTask.setDeadline(taskEditRequest.getDeadline());

    taskRepository.deleteById(taskEditRequest.getId());
    taskRepository.save(newTask);

    log.info(newTask);
  }

  public Task getTaskById(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
  }

}
