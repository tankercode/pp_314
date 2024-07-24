package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UsersService;

import javax.management.relation.RoleNotFoundException;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping(value = "/")
    public String doDefaultRedirect() {
        return "redirect:/admin/main";
    }
    @GetMapping(value = "/admin/main")
    public String showUsersTable(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "/admin/main";
    }

    @GetMapping(value = "/admin/new")
    public String showCreateUserPage(@ModelAttribute("user") User user) {
        return "/admin/new";
    }

    @PostMapping
    public String createNewUser(@ModelAttribute("user") User user) {
        try {
            usersService.save(user);
        } catch (RoleNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin/edit")
    public String showEditUserPage(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", usersService.findOne(id));
        return "/admin/edit";
    }

    @PatchMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("id") int id) {
        usersService.update(id, user);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin/delete")
    @DeleteMapping()
    public String delete(@RequestParam("id") int id) {
        usersService.deleteUserById(id);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/user")
    public String showUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }

}
