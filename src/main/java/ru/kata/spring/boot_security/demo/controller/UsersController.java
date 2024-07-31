package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Controller
public class UsersController {

    private final UsersServiceImp usersServiceImp;

    @Autowired
    public UsersController(UsersServiceImp usersServiceImp) {
        this.usersServiceImp = usersServiceImp;

        Role admin_role = new Role();
        admin_role.setType("ROLE_ADMIN");
        usersServiceImp.save(admin_role);

        Role user_role = new Role();
        user_role.setType("ROLE_USER");
        usersServiceImp.save(user_role);

        User user = new User();
        user.setAge(10);
        user.setPass("password");
        user.setName("user");
        user.setLastname("lastname");
        user.setEmail("mail@mail.ru");
        usersServiceImp.save(user);

        User user1 = new User();
        user1.setAge(11);
        user1.setPass("password");
        user1.setName("admin");
        user1.setLastname("lastname1");
        user1.setEmail("mail@mail1.ru");
        user1.setRole(List.of(admin_role));
        usersServiceImp.save(user1);

    }

    @GetMapping(value = "/")
    public String doDefaultRedirect() {
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin/main")
    public String showUsersTable(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", usersServiceImp.findAll());
        model.addAttribute("currentUser", user);
        return "/admin/main";
    }

    @GetMapping(value = "/admin/new")
    public String showCreateUserPage(@ModelAttribute("user") User user,
                                     @AuthenticationPrincipal User currentUser,
                                     Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allRoles", usersServiceImp.findAllRoles());
        return "/admin/new";
    }

    @PostMapping(value = "/admin/new")
    public String createNewUser(@ModelAttribute("user") User user,
    @RequestParam(value = "roleIds") int[] roles) {
        usersServiceImp.save(user, roles);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin/edit")
    public String showEditUserPage(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", usersServiceImp.findOne(id));
        return "/admin/edit";
    }

    @PatchMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("id") int id) {
        usersServiceImp.update(id, user);
        return "redirect:/admin/main";
    }
    @DeleteMapping(value = "/admin/delete")
    public String delete(@RequestParam("id") int id) {
        usersServiceImp.deleteUserById(id);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/user")
    public String showUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }

}
