package com.example.todolist.controller;

import com.example.todolist.model.TransferRequest;
import com.example.todolist.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    private TransferRequest transferRequest;

    @PostMapping("/move")
    public void transferRowController(@RequestBody TransferRequest request){
        transferService.transferRow(request.getId());
    }

}
