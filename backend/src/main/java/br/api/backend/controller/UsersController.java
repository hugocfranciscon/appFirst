package br.api.backend.controller;

import br.api.backend.model.Users;
import br.api.backend.service.UsersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController { 
    @Autowired
    UsersService userService;
            
    @GetMapping("/api/users")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Users> getAll() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{userId}")
    public Users getUser(@PathVariable Long userId) {
        return null;
    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return null;
    }

    @PutMapping("/{userId}")
    public Users updateUser(@PathVariable Long userId, @RequestBody Users user) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
    }    
}
