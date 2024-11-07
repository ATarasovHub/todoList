package com.example.todolist.controller;

import com.example.todolist.model.users.UsersRecord;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/Users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UsersRecord> getUsersByID(@PathVariable Long id) {
    UsersRecord usersRecord = userService.getUserById(id);
    return ResponseEntity.ok(usersRecord);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UsersRecord loginRequest) {
    UsersRecord user = userService.findByUsername(loginRequest.getUsername());

    return  ResponseEntity.ok(user);
  }
}
