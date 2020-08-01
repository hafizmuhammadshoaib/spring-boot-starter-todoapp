package com.example.TodoApp.Services.Mappers;

import com.example.TodoApp.DTOs.UserMsDTO;
import com.example.TodoApp.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserMsDTO userToUserMsDto(User user);

    List<UserMsDTO> usersToUserDtos(List<User>users);
}
