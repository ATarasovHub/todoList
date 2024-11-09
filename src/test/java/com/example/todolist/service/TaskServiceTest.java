package com.example.todolist.service;

import com.example.todolist.model.task.Task;
import com.example.todolist.model.task.TaskEditRequest;
import com.example.todolist.model.task.TaskRecord;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetTaskById_nonExistentTaskId_throwsException() {
        Long nonExistentTaskId = 999L;
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(nonExistentTaskId));

        assertThat(exception.getMessage()).isEqualTo("Task not found");
    }

    @Test
    void testGetAllTasks_noTasksInDatabase_returnsEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskRecord> result = taskService.getAllTasks();

        assertThat(result).isEmpty();
    }

    @Test
    void testSaveTask_withNullName_throwsException() {
        TaskRecord taskRecordWithNullDeadline = TaskRecord.builder().id(1L).name("Sample Task").deadline(null).build();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> taskService.saveTask(taskRecordWithNullDeadline));

        assertThat(exception.getMessage()).contains("Deadline must not be null");
    }

    @Test
    void testSaveTask_withNullDeadline_throwsException() {
        TaskRecord taskRecordWithNullDeadline = TaskRecord.builder().id(1L).name("Task").deadline(null).build();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> taskService.saveTask(taskRecordWithNullDeadline));

        assertThat(exception.getMessage()).contains("Deadline must not be null");
    }

    @Test
    void testEditTask_nonExistentTaskId_throwsException() {
        Long nonExistentTaskId = 999L;
        TaskEditRequest taskEditRequest = TaskEditRequest.builder()
                .name("Updated Task")
                .deadline(LocalDateTime.now())
                .build();
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> taskService.editTask(nonExistentTaskId, taskEditRequest));

        assertThat(exception.getMessage()).isEqualTo("Task not found");
    }

    @Test
    void testEditTask_withNullName_throwsException() {
        Long existingTaskId = 1L;
        TaskEditRequest taskEditRequestWithNullName = TaskEditRequest.builder()
                .name(null)
                .deadline(LocalDateTime.now())
                .build();
        when(taskRepository.findById(existingTaskId)).thenReturn(Optional.of(
                Task.builder().id(existingTaskId).name("Task").deadline(LocalDateTime.now()).build()
        ));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> taskService.editTask(existingTaskId, taskEditRequestWithNullName));

        assertThat(exception.getMessage()).contains("Name must not be null");
    }

    @Test
    void testEditTask_withNullDeadline_throwsException() {
        Long existingTaskId = 1L;
        TaskEditRequest taskEditRequestWithNullDeadline = TaskEditRequest.builder()
                .name("Updated Task")
                .deadline(null)
                .build();
        when(taskRepository.findById(existingTaskId)).thenReturn(Optional.of(
                Task.builder().id(existingTaskId).name("Task").deadline(LocalDateTime.now()).build()
        ));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> taskService.editTask(existingTaskId, taskEditRequestWithNullDeadline));

        assertThat(exception.getMessage()).contains("Deadline must not be null");
    }

    @Test
    void testGetAllTasks_multipleTasksInDatabase_returnsAllTasks() {
        LocalDateTime fixedTime1 = LocalDateTime.of(2024, 11, 9, 19, 44, 21);
        LocalDateTime fixedTime2 = fixedTime1.plusDays(1);

        Task task1 = Task.builder().id(1L).name("Task 1").deadline(fixedTime1).build();
        Task task2 = Task.builder().id(2L).name("Task 2").deadline(fixedTime2).build();
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskRecord> result = taskService.getAllTasks();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(
                TaskRecord.builder().id(1L).name("Task 1").deadline(fixedTime1).build(),
                TaskRecord.builder().id(2L).name("Task 2").deadline(fixedTime2).build()
        );
    }


    @Test
    void testGetTaskById_deletedTaskId_throwsException() {
        Long deletedTaskId = 1L;
        when(taskRepository.findById(deletedTaskId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(deletedTaskId));

        assertThat(exception.getMessage()).isEqualTo("Task not found");
    }

    @Test
    void testEditTask_withExistingTaskId_updatesTask() {
        LocalDateTime fixedTime1 = LocalDateTime.of(2024, 11, 9, 19, 44, 21);

        Long existingTaskId = 1L;
        TaskEditRequest taskEditRequest = TaskEditRequest.builder()
                .name("Updated Task")
                .deadline(fixedTime1)
                .build();
        Task existingTask = Task.builder().id(existingTaskId).name("Task").deadline(fixedTime1.minusDays(1)).build();
        when(taskRepository.findById(existingTaskId)).thenReturn(Optional.of(existingTask));

        taskService.editTask(existingTaskId, taskEditRequest);

        ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("Updated Task");
        assertThat(argumentCaptor.getValue().getDeadline()).isEqualTo(fixedTime1);
    }
}
