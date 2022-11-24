package com.example.quanlycuahangmaytinh.Services;

import com.example.quanlycuahangmaytinh.DTO.UserDTO;
import com.example.quanlycuahangmaytinh.Models.User;
import com.example.quanlycuahangmaytinh.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean signUp(Map<String,String> userData){

        if(!userRepository.findByEmail(userData.get("email")).isEmpty()){
            return false;
        }
        User user = new User();
        user.setName(userData.get("name"));
        user.setPassWord(userData.get("password"));
        user.setEmail(userData.get("email"));
        userRepository.save(user);
        return true;
    }
    public UserDTO login(Map<String,String> loginData){
        if(userRepository.findByEmailAndPassWord(loginData.get("email"),loginData.get("password")).isPresent()){
            User  user =  userRepository.findByEmailAndPassWord(loginData.get("email"),loginData.get("password")).get();
            return new UserDTO(user.getEmail(), user.getName());
        }
        return null;
    }
    public boolean checkInfoAuth(String email, String userName){
        if(userRepository.findByEmailAndUserName(email, userName).isPresent())
            return true;
        return false;
    }
}
