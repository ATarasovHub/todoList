package com.example.todolist.service;

import com.example.todolist.model.History;
import com.example.todolist.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveHistory() {
        History history = History.builder()
                .id(1L)
                .name("New History")
                .deadline(LocalDateTime.now())
                .build();

        when(historyRepository.save(any(History.class))).thenReturn(history);

        History savedHistory = historyService.saveHistory(history);

        assertEquals(history.getId(), savedHistory.getId());
        assertEquals(history.getName(), savedHistory.getName());
        assertEquals(history.getDeadline(), savedHistory.getDeadline());
    }
}
