package com.example.todolist.service;

import com.example.todolist.model.History;
import com.example.todolist.model.Task;
import com.example.todolist.repository.HistoryRepository;
import com.example.todolist.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void transferRow(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));

        History history = new History();
        history.setId(task.getId());
        history.setName(task.getName());
        history.setDeadline(task.getDeadline());

        historyRepository.save(history);
        taskRepository.deleteById(id);
    }
}
