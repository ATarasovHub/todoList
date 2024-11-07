package com.example.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.todolist.model.history.History;
import com.example.todolist.model.history.HistoryRecord;
import com.example.todolist.repository.HistoryRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

  @Mock private HistoryRepository historyRepository;

  @InjectMocks private HistoryService historyService;

  @Test
  public void testSaveHistory() {
    History history =
        History.builder().id(1L).name("New History").deadline(LocalDateTime.now()).build();

    when(historyRepository.save(any(History.class))).thenReturn(history);

    HistoryRecord savedHistory =
        historyService.saveHistory(historyService.toHistoryRecord(history));

    assertEquals(history.getId(), savedHistory.getId());
    assertEquals(history.getName(), savedHistory.getName());
    assertEquals(history.getDeadline(), savedHistory.getDeadline());
  }
}
