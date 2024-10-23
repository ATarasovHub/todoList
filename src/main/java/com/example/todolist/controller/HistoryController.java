package com.example.todolist.controller;

import com.example.todolist.model.History;
import com.example.todolist.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<History> getAllHistory() {
        return historyService.getAllHistory();
    }

    @PostMapping("/createHistory")
    public History createHistory(@RequestBody History history) {
        return historyService.saveHistory(history);
    }

}
