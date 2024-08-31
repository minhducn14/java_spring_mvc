package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/") // GET
    public String getHomePage(Model model) {
        List<User> users = this.userService.getAllUser();
        System.out.println("Users: " + users);

        List<User> usersByEmail = this.userService.getAllUserByEmail("2@gmail.com");
        System.out.println("Users by email: " + usersByEmail);
        model.addAttribute("hello", "hello");
        return "hello";
    }

    @RequestMapping("/admin/user") // GET
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        System.out.println("Users: " + users);
        model.addAttribute("users", users);
        return "/admin/user/table-user";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // POST
    public String createUserPage(Model model, @ModelAttribute("newUser") User user) {
        this.userService.handlerSaveUser(user);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String updateUserPage(@PathVariable Long id, Model model) {
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUserPage(Model model, @ModelAttribute("newUser") User user) {
        User userUpdate = this.userService.getUserById(user.getId());
        if (userUpdate != null) {
            userUpdate.setFullName(user.getFullName());
            userUpdate.setPhone(user.getPhone());
            userUpdate.setAddress(user.getAddress());
            this.userService.handlerSaveUser(userUpdate);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUserPage(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUserPagePost(Model model, @ModelAttribute("newUser") User user) {
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

}

// @RestController
// public class UserController {
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handlerHello();
// }
// }