package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// @Controller
// public class UserController {

import vn.hoidanit.laptopshop.service.UserService;

//     @RequestMapping("/")
//     public String getHomePage() {
//         return "get home page";
//     }

// }

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return this.userService.handlerHello();
    }

}
