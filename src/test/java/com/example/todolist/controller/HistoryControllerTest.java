package com.example.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.todolist.model.history.HistoryRecord;
import com.example.todolist.service.HistoryService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private HistoryService historyService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllHistory() throws Exception {
    HistoryRecord history1 =
        HistoryRecord.builder().id(1L).name("Test History 1").deadline(LocalDateTime.now()).build();

    HistoryRecord history2 =
        HistoryRecord.builder()
            .id(2L)
            .name("Test History 2")
            .deadline(LocalDateTime.now().plusDays(1))
            .build();

    List<HistoryRecord> historyList = Arrays.asList(history1, history2);

    when(historyService.getAllHistory()).thenReturn(historyList);

    mockMvc
        .perform(get("/history").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Test History 1"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].name").value("Test History 2"));
  }

  @Test
  public void testCreateHistory() throws Exception {
    HistoryRecord history =
        HistoryRecord.builder().id(1L).name("New History").deadline(LocalDateTime.now()).build();

    when(historyService.saveHistory(any(HistoryRecord.class))).thenReturn(history);

    mockMvc
        .perform(
            post("/history/createHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New History\",\"deadline\":\"2024-10-24T10:00:00\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("New History"));
  }
}
