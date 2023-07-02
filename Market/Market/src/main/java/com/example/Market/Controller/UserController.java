package com.example.Market.Controller;


import com.example.Market.Dto.UserDto;
import com.example.Market.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private  final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users= userService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDto> FindUser(@PathVariable("id") Long id){
        Optional<UserDto> user=userService.findUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addUser")
    //User doit être désérialisé (Conversion d'un objet en Json ou xml en objet Java )
    public  ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto, HttpServletResponse response){
        UserDto newUser =userService.addUser(userDto)  ;
        return new ResponseEntity<>(newUser,HttpStatus.CREATED );
    }
    @PutMapping("/update")
    public ResponseEntity<UserDto> UpdateUser(@RequestBody UserDto userDto , HttpServletResponse response){
        UserDto updateUser=userService.UpdateUser(userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id){
        userService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
