package com.example.todolist.service;

import com.example.todolist.model.users.Users;
import com.example.todolist.model.users.UsersRecord;
import com.example.todolist.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;

  public UsersRecord findByUsername(String username) {
    Users user =
        usersRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return toUsersRecord(user);
  }

  public UsersRecord getUserById(Long id) {
    return toUsersRecord(
        usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Users not found")));
  }

  public UsersRecord toUsersRecord(Users users) {
    return UsersRecord.builder().id(users.getId()).password(users.getPassword()).build();
  }

  public Users toUsers(UsersRecord usersRecord) {
    return Users.builder().id(usersRecord.getId()).password(usersRecord.getPassword()).build();
  }
}
