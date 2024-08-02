package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersServiceImp usersServiceImp;

    @GetMapping(value = "/")
    public String doDefaultRedirect() {
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin/main")
    public String showUsersTable(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", usersServiceImp.findAll());
        model.addAttribute("currentUser", user);
        model.addAttribute("allRoles", usersServiceImp.findAllRoles());
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
                         @RequestParam("roles") int[] rolesIds) {
        usersServiceImp.update(user, rolesIds);
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
