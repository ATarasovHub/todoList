package com.example.todolist.controller;

import com.example.todolist.model.history.HistoryRecord;
import com.example.todolist.service.HistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public List<HistoryRecord> getAllHistory() {
        return historyService.getAllHistory();
    }

    @PostMapping("/createHistory")
    public HistoryRecord createHistory(@RequestBody HistoryRecord history) {
        return historyService.saveHistory(history);
    }

}
