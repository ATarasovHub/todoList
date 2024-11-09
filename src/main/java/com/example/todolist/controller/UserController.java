package com.example.todolist.controller;

import com.example.todolist.model.users.UsersRecord;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UsersRecord> getUsersByID(@PathVariable Long id) {

    UsersRecord usersRecord = userService.getUserById(id);
    if(usersRecord == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(usersRecord);
  }

  @PostMapping("/login")
  public ResponseEntity<UsersRecord> login(@RequestBody UsersRecord loginRequest) {
    log.info("Login attempt for user: {}", loginRequest.getUsername());

    if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

    if (isAuthenticated) {
      UsersRecord user = userService.findByUsername(loginRequest.getUsername());
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }





  @GetMapping
  public ResponseEntity< List<UsersRecord>> getAllUsers(){
    List<UsersRecord> infoUsers =  userService.getAllUsers();
    log.info("Users Check from UserController:{}" , infoUsers);
    return ResponseEntity.status(HttpStatus.OK).body(infoUsers);
  }
}
