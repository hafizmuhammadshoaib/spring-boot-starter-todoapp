package com.example.TodoApp.Services;

import com.example.TodoApp.DTOs.UserDTOMapper;
import com.example.TodoApp.DTOs.UserMsDTO;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UniqueConstraintException;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.UserRepository;
import com.example.TodoApp.Services.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserMsDTO> getAllUsers() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    public UserMsDTO getUserById(int id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        return userMapper.userToUserMsDto(user);

    }

    public User createUser(User user) throws  UniqueConstraintException{

            if(userRepository.existsByEmail(user.getEmail())){
                throw new UniqueConstraintException("Email already exist");
            }
            return userRepository.save(user);

    }

    public User updateUser(int id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
