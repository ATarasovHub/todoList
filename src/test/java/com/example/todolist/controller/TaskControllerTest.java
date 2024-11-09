package com.example.todolist.controller;

import com.example.todolist.model.task.TaskEditRequest;
import com.example.todolist.model.task.TaskRecord;
import com.example.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        // Reset any state before each test
    }

    @Test
    void getAllTasks_shouldReturnEmptyList_whenNoTasksPresent() {
        // Given
        List<TaskRecord> expectedTasks = List.of();
        when(taskService.getAllTasks()).thenReturn(expectedTasks);

        // When
        List<TaskRecord> actualTasks = taskController.getAllTasks();

        // Then
        assertEquals(expectedTasks, actualTasks);
        assertTrue(actualTasks.isEmpty());
    }

    @Test
    void editTask_shouldValidateInput_whenEditingTask() {
        // Given
        Long taskId = 1L;
        TaskEditRequest taskEditRequest = TaskEditRequest.builder()
                .name("Edited task")
                .deadline(null)
                .build();

        // When
        taskController.editTask(taskId, taskEditRequest);

        // Then
        // Validate input using Mockito verify or ArgumentCaptor
        verify(taskService).editTask(eq(taskId), eq(taskEditRequest));
        // Add more assertions if necessary
    }

    @Test
    void createTask_shouldValidateInput_whenCreatingNewTask() {
        // Given
        TaskRecord newTask = TaskRecord.builder()
                .id(1L)
                .deadline(null)
                .name("test")
                .build();

        // When
        ResponseEntity<TaskRecord> responseEntity = taskController.createTask(newTask);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(taskService).saveTask(eq(newTask));
        // Add more assertions if necessary
    }


}