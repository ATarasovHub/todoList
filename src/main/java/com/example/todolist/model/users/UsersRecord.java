package com.example.todolist.model.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersRecord {
    private Long id;

    private String username;

    private String password;
}
