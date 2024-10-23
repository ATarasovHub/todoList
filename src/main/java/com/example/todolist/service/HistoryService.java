package com.example.todolist.service;

import com.example.todolist.model.History;
import com.example.todolist.repository.HistoryRepository;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

  @Autowired private HistoryRepository historyRepository;

  public List<History> getAllHistory() {
    return historyRepository.findAll();
  }

  public History saveHistory(History history) {
    return historyRepository.save(history);
  }
}
