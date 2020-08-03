package com.example.TodoApp.Services;

import com.example.TodoApp.DTOs.SignInReqDTO;
import com.example.TodoApp.DTOs.SignInResDTO;
import com.example.TodoApp.DTOs.UserMsDTO;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UniqueConstraintException;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.UserRepository;
import com.example.TodoApp.Security.jwt.JwtUtils;
import com.example.TodoApp.Services.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

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

    public UserMsDTO createUser(User user) throws  UniqueConstraintException{

            if(userRepository.existsByEmail(user.getEmail())){
                throw new UniqueConstraintException("Email already exist");
            }
            User _user = new User();
            _user.setEmail(user.getEmail());
            _user.setName(user.getName());
            _user.setPassword(encoder.encode(user.getPassword()));
            return userMapper.userToUserMsDto(userRepository.save(_user));

    }

    public User updateUser(int id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public SignInResDTO sigInUser(SignInReqDTO user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SignInResDTO signInResDTO = new SignInResDTO(userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),jwt);
        return userMapper.userToSignInDto(signInResDTO);

    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
