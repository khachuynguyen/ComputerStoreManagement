package com.example.quanlycuahangmaytinh.Controllers.Mvc;

import com.example.quanlycuahangmaytinh.DTO.UserDTO;
import com.example.quanlycuahangmaytinh.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/signUp")
    public String signUpPage(){
        return "signup";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/signUp")
    public String signUp(@RequestParam Map<String,String> userData, Model model){
        boolean check;
        if( !userData.get("password").equals(userData.get("reTypePassword"))  )
            return "signup";
        check =  userService.signUp(userData);
        if(check)
            return "redirect:/login";
        return "signup";
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> loginData, HttpSession session){
        UserDTO auth = userService.login(loginData);
        if(auth!=null){
            session.setAttribute("auth",auth);
            session.setAttribute("userName",auth.getName());
            return "redirect:/management";
        }
        return "/login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
