package com.example.todolist.service;

import com.example.todolist.model.history.History;
import com.example.todolist.model.history.HistoryRecord;
import com.example.todolist.repository.HistoryRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

  private final HistoryRepository historyRepository;

  public List<HistoryRecord> getAllHistory() {
    List<History> historyList = historyRepository.findAll();
    return historyList.stream().map(this::toHistoryRecord).collect(Collectors.toList());
  }

  public HistoryRecord saveHistory(HistoryRecord historyRecord) {
    History history = historyRepository.save(toHistory(historyRecord));
    return toHistoryRecord(history);
  }

  public HistoryRecord toHistoryRecord(History history) {
    return HistoryRecord.builder()
        .id(history.getId())
        .name(history.getName())
        .deadline(history.getDeadline())
        .build();
  }

  public History toHistory(HistoryRecord historyRecord) {
    return History.builder()
        .id(historyRecord.getId())
        .name(historyRecord.getName())
        .deadline(historyRecord.getDeadline())
        .build();
  }
}
