package br.api.backend.controller;

import br.api.backend.JwtService;
import br.api.backend.model.Users;
import br.api.backend.service.UsersService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    
    @Autowired
    UsersService userService;
    
    @PostMapping(path = "/token", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getToken(@RequestBody Map<String, Object> claims) {
        
        System.out.println(claims);
        System.out.println(claims.get("user"));        
        Users user = this.userService.getUserByUsernameAndPassword(claims.get("user").toString(), claims.get("pass").toString());        
        
        if (user != null && user.getUsername().equals(claims.get("user")) && user.getPassword().equals(claims.get("pass"))) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "ok");
            response.put("token", jwtService.generateJWT(claims));
            response.put("userId", user.getId());
            response.put("type", user.getType());
            
            return ResponseEntity.ok(response);
        } 
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", "Acesso Negado"));
    }

}
