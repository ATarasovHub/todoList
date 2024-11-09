package com.example.todolist.service;

import com.example.todolist.model.users.Users;
import com.example.todolist.model.users.UsersRecord;
import com.example.todolist.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public boolean authenticate(String username, String password) {
        Users user = usersRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public UsersRecord findByUsername(String username) {
        Users user = usersRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return UsersRecord.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        }
        return null;
    }


    public UsersRecord getUserById(Long id) {
        return toUsersRecord(
                usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Users not found")));
    }

    public List<UsersRecord> getAllUsers() {

        List<Users> users = usersRepository.findAll();


        return users.stream()
                .map(this::toUsersRecord)
                .collect(Collectors.toList());
    }

    public UsersRecord toUsersRecord(Users users) {
        return UsersRecord.builder().id(users.getId()).password(users.getPassword()).build();
    }

    public Users toUsers(UsersRecord usersRecord) {
        return Users.builder().id(usersRecord.getId()).password(usersRecord.getPassword()).build();
    }
}
