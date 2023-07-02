package com.example.Market.Services;

import com.example.Market.Dto.UserDto;
import com.example.Market.Exceptions.EntityNotFoundException;
import com.example.Market.Exceptions.ErrorsCode;
import com.example.Market.Model.User;
import com.example.Market.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepo  userRepo;
    private final ModelMapper modelMapper; // to convert class User to UserDto

    //Convert UserDto to User
//then reconvert User to UserDto to return UserDto
    public UserDto addUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

//converting an optional<User> to optional<UserDto> is not directly supported by ModelMapper.
    public Optional<UserDto> findUserById(Long id) {
        if (id==0 ){log.error("l'id est null");}
        Optional<User> user = userRepo.findById(id);
        return Optional.ofNullable(user.map(u -> modelMapper.map(u, UserDto.class)).orElseThrow(() -> new EntityNotFoundException("user " + id + "not found", ErrorsCode.USER_NOT_FOUND)));
    }
//Converting a List<User> to List<UserDto> to return List<UserDto>
    public List<UserDto> findAllUser(){
        List<User> users =userRepo.findAll();
        return users.stream()
                .map(u-> modelMapper.map(u,UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto UpdateUser(UserDto userDto){
      User user =modelMapper.map(userDto,User.class);
      user.setFirstname(user.getFirstname());
      user.setLastname(user.getLastname());
      user.setAddress(user.getAddress());
      user.setPhoneNum(user.getPhoneNum());
      User updatedUser= userRepo.save(user);
      return modelMapper.map(updatedUser,UserDto.class);
    }

    public void DeleteById(long id){
        if (id==0 ){log.error("l'id est null");}
        userRepo.deleteUserById(id);
    }

}
