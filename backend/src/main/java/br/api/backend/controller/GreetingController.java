package br.api.backend.controller;

import br.api.backend.model.Users;
import br.api.backend.service.UsersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Autowired
    UsersService userService;
        
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_GUEST')")
    public List<Users> getAll(Authentication authentication) {
        return userService.getAllUsers();
    }

}